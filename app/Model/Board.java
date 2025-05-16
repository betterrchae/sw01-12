package app.Model;

import app.Model.Enum.BoardType;
import app.Model.Enum.YutResult;
import app.Model.Horse.Horse;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {
    private final BoardType type;
    private final List<Spot> spots;
    private final List<Line> lines;
    private final List<Path> paths;
    private final Spot finishSpot;
    private final Map<Integer, List<Horse>> horsePositions;
    private final Map<Spot, Point> spotPositions;


    public Board(BoardType type, List<Spot> spots, List<Line> lines, List<Path> paths, Map<Spot, Point> spotPositions) {
        this.type = type;
        this.spots = new ArrayList<>(spots);
        this.lines = new ArrayList<>(lines);
        this.paths = new ArrayList<>(paths);
        this.horsePositions = new HashMap<>();
        this.spotPositions = new HashMap<>(spotPositions);

        Spot finish = null;
        for (Spot spot : spots) {
            if (spot.isFinish()) {
                finish = spot;
            }
        }
        this.finishSpot = finish;

        confirmBackdoConnections();
    }

    private void confirmBackdoConnections() {
        for (Spot spot : spots) {
            if (spot.getPrevSpot() == null && !spot.isStart()) {
                for (Spot other : spots) {
                    if (other.getNextSpot(YutResult.DO) == spot) {
                        spot.setPrevSpot(other);
                        break;
                    }
                }
            }
        }
    }

    public Point getSpotPosition(Spot spot) {
        return spotPositions.get(spot);
    }

    public List<Spot> getSpots() {
        return Collections.unmodifiableList(spots);
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }

    public List<Horse> getHorsesAtSpot(Spot spot) {
        if (spot == null) return Collections.emptyList();
        int id = spot.getId();
        return horsePositions.containsKey(id) ? Collections.unmodifiableList(horsePositions.get(id)) : Collections.emptyList();
    }

    public void updateHorsePosition(Horse horse) {

        if (horse.isFinished()) {
            horsePositions.values().forEach(list -> list.remove(horse));
            return;
        }
        // 1) 모든 스팟에서 해당 말을 제거해 이전 위치 기록을 삭제
        horsePositions.values().forEach(list -> list.remove(horse));
        // 2) 현재 위치(새 스팟)에 말 추가
        Spot spot = horse.getCurrentSpot();
        if (spot == null) return;
        int id = spot.getId();
        horsePositions.computeIfAbsent(id, k -> new ArrayList<>()).add(horse);
    }

    public Spot calculateNextSpot(Horse horse, Spot currentSpot, YutResult result) {
        // 출발 전인 경우
        if (currentSpot == null) {
            // 시작점에서 출발
            return calculateFirstMove(result);
        }

        // 이미 도착한 말은 더 이상 이동할 수 없음
        if (currentSpot.isFinish()) {
            return null;
        }

        // 빽도인 경우
        if (result == YutResult.BACKDO) {
            Path currentPath = horse.getCurrentPath();

            // 대각선 처리
            if (currentPath != null && currentPath.getSpots().contains(currentSpot)) {
                int index = currentPath.getSpots().indexOf(currentSpot);
                if (index > 0) {
                    return currentPath.getSpots().get(index - 1);
                } else {
                    return null;
                }
            }

            return currentSpot.getPrevSpot();
        }

        if (currentSpot.isStart() && !currentSpot.equals(calculateFirstMove(YutResult.DO))) {
            return finishSpot;
        }

        // 3) Shortcut 경로 찾기 (진입 지점 또는 중간 지점)
        boolean isEntry = currentSpot.hasPath(result);
        Path path = isEntry ? currentSpot.getNextPath(result) : null;
        if (path == null) {
            for (Path p : paths) {
                if (p.isShortcut() && p.getSpots().contains(currentSpot)) {
                    path = p;
                    break;
                }
            }
        }

        Spot dest;
        if (path != null && path.isShortcut()) {
            int moveCount = result.getMoveCount();
            horse.setCurrentPath(path);

            // 4-b) 중앙에 “딱” 멈출 경우 우선 처리
            int currIdx = path.getSpots().indexOf(currentSpot);


            // 4-c) 대각선 내 일반 이동 시도
            dest = moveAlongPath(path, currentSpot, moveCount);
            if (dest != null) {
                return dest;
            }

            // 4-d) 오버슈트 발생 시 메인 루트로 복귀
            int stepsToEnd = path.getSpots().size() - 1 - currIdx;
            int overshoot = moveCount - stepsToEnd;
            dest = moveAlongMainPath(path.getLastSpot(), overshoot);
        } else {
            // 5) 일반 메인 경로
            dest = moveAlongMainPath(currentSpot, result.getMoveCount());
        }


        return dest;
    }

    private Spot moveAlongPath(Path path, Spot fromSpot, int moveCount) {
        if (path == null) return null;
        return path.getSpotAfterMove(fromSpot, moveCount);
    }

    private Spot moveAlongMainPath(Spot startSpot, int rem) {
        Spot s = startSpot;
        for (int i = 0; i < rem; i++) {
            Spot next = s.getNextSpot(YutResult.DO);
            // “다음이 없다” → 곧바로 도착 처리
            if (next == null) {
                return finishSpot;
            }
            s = next;
        }
        return s;
    }

    private Spot calculateFirstMove(YutResult result) {
        if (result == YutResult.BACKDO) return null;
        Path mainPath = paths.stream().filter(p -> !p.isShortcut() && "main".equals(p.getName())).findFirst().orElse(null);
        if (mainPath == null) return null;
        List<Spot> ps = mainPath.getSpots();
        int idx = Math.min(result.getMoveCount(), ps.size() - 1);
        return ps.get(idx);
    }
}
