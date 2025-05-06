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

    // 이미 도착한 말은 더 이상 이동할 수 없음
    if (currentSpot.isFinish()) {
      return null;
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

    // 이동할 칸 수
    int steps = result.getMoveCount();
    Spot nextSpot = currentSpot;

    // 한 칸씩 이동하면서 시작 지점을 지나는지 확인
    boolean passedStart = false;

    for (int i = 0; i < steps; i++) {
      // 첫 번째 이동에서만 특별 경로 확인 (모서리에서의 지름길)
      if (i == 0 && nextSpot.hasPath(result)) {
        Path path = nextSpot.getNextPath(result);
        nextSpot = path.getFirstSpot();
      } else {
        // 기본 다음 칸으로 이동
        Spot tempNext = nextSpot.getNextSpot(YutResult.DO);

        // 다음 칸이 없으면 도착으로 처리
        if (tempNext == null) {
          return finishSpot;
        }

        nextSpot = tempNext;
      }

      // 시작 지점을 지나면 표시
      if (nextSpot.isStart()) {
        passedStart = true;
      }

      // 도착 지점을 지나면 바로 도착으로 처리
      if (nextSpot.isFinish()) {
        return finishSpot;
      }
    }

    // 시작 지점을 지났으면 한 바퀴를 돈 것이므로 도착으로 처리하지 않음
    // 윷놀이 규칙에 따라 한 바퀴를 돌아도 계속 진행

    return nextSpot;
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
