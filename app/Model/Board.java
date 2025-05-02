package app.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.Model.Enum.BoardType;
import app.Model.Enum.YutResult;

public class Board {
  private final BoardType type;
  private final List<Spot> spots;
  private final List<Line> lines;
  private final List<Path> paths;
  private final Spot startSpot;
  private final Spot finishSpot;
  private final Map<Integer, List<Horse>> horsePositions;

  public Board(BoardType type, List<Spot> spots, List<Line> lines, List<Path> paths) {
    this.type = type;
    this.spots = new ArrayList<>(spots);
    this.lines = new ArrayList<>(lines);
    this.paths = new ArrayList<>(paths);
    this.horsePositions = new HashMap<>();

    // 시작 지점과 도착 지점 설정
    Spot start = null;
    Spot finish = null;
    for (Spot spot : spots) {
      if (spot.isStart()) {
        start = spot;
      }
      if (spot.isFinish()) {
        finish = spot;
      }
    }

    this.startSpot = start;
    this.finishSpot = finish;

    confirmBackdoConnections();
  }

  private void confirmBackdoConnections() {
    for (Spot spot : spots) {
      // Spot 클래스에 이미 setPrevSpot이 호출되었는지 확인
      if (spot.getPrevSpot() == null && !spot.isStart()) {
        // 이 칸이 다른 칸의 다음 칸인지 확인
        for (Spot other : spots) {
          if (other.getNextSpot(YutResult.DO) == spot) {
            spot.setPrevSpot(other);
            break;
          }
        }
      }
    }
  }

  public BoardType getType() {
    return type;
  }

  public List<Spot> getSpots() {
    return Collections.unmodifiableList(spots);
  }

  public List<Line> getLines() {
    return Collections.unmodifiableList(lines);
  }

  public List<Path> getPaths() {
    return Collections.unmodifiableList(paths);
  }

  public Spot getStartSpot() {
    return startSpot;
  }

  public Spot getFinishSpot() {
    return finishSpot;
  }

  public void updateHorsePosition(Horse horse) {
    Spot spot = horse.getCurrentSpot();
    if (spot == null) {
      return;
    }

    int spotId = spot.getId();
    if (!horsePositions.containsKey(spotId)) {
      horsePositions.put(spotId, new ArrayList<>());
    }

    // 기존 위치에서 제거
    for (List<Horse> horses : horsePositions.values()) {
      horses.remove(horse);
    }

    // 새 위치에 추가
    horsePositions.get(spotId).add(horse);
  }

  public List<Horse> getHorsesAtSpot(Spot spot) {
    if (spot == null) {
      return Collections.emptyList();
    }

    int spotId = spot.getId();
    if (!horsePositions.containsKey(spotId)) {
      return Collections.emptyList();
    }

    return Collections.unmodifiableList(horsePositions.get(spotId));
  }

  public Spot getSpotById(int id) {
    for (Spot spot : spots) {
      if (spot.getId() == id) {
        return spot;
      }
    }
    return null;
  }

  public Spot calculateNextSpot(Spot currentSpot, YutResult result) {
    // 출발 전인 경우
    if (currentSpot == null) {
      // 시작점에서 출발
      return calculateFirstMove(result);
    }

    // 빽도인 경우
    if (result == YutResult.BACKDO) {
      // 시작점에서는 빽도 사용 불가
      if (currentSpot.isStart()) {
        return null;
      }

      // 이전 칸으로 이동
      return currentSpot.getPrevSpot();
    }

    // 특별 경로 확인 (모서리에서의 지름길)
    if (currentSpot.hasPath(result)) {
      Path path = currentSpot.getNextPath(result);
      return path.getFirstSpot();
    }

    // 기본 다음 칸 반환
    return currentSpot.getNextSpot(result);
  }

  private Spot calculateFirstMove(YutResult result) {
    // 출발점에서 윷 결과에 따라 이동 계산
    switch (result) {
      case BACKDO:
        // 빽도는 출발에서는 이동 불가
        return null;
      case DO:
      case GAE:
      case GEOL:
      case YUT:
      case MO:
        // 기본 경로로 이동
        Path mainPath = paths.stream()
            .filter(p -> !p.isShortcut() && "main".equals(p.getName()))
            .findFirst()
            .orElse(null);

        if (mainPath == null) {
          return null;
        }

        List<Spot> pathSpots = mainPath.getSpots();
        int targetIndex = Math.min(result.getMoveCount(), pathSpots.size() - 1);
        return pathSpots.get(targetIndex);
      default:
        return null;
    }
  }
}
