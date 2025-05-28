package app.Model;

import app.Model.Enum.YutResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 도메인 모델의 Spot 클래스 (UI 좌표 제거)
 */
public class Spot {
    private final int id;
    private final boolean isCorner;
    private final boolean isStart;
    private final boolean isFinish;
    private final Map<YutResult, Spot> nextSpots = new HashMap<>();
    private final Map<YutResult, Path> nextPaths = new HashMap<>();
    private Spot prevSpot;

    /**
     * @param id       칸 고유 아이디
     * @param isCorner 모서리 칸 여부
     * @param isStart  시작 칸 여부
     * @param isFinish 도착 칸 여부
     */
    public Spot(int id, boolean isCorner, boolean isStart, boolean isFinish) {
        this.id = id;
        this.isCorner = isCorner;
        this.isStart = isStart;
        this.isFinish = isFinish;
    }

    public int getId() {
        return id;
    }

    public boolean isCorner() {
        return isCorner;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isFinish() {
        return isFinish;
    }

    /**
     * 기본 이동 경로 추가 (DO 결과에 대해 prevSpot 설정 포함)
     */
    public void addNextSpot(YutResult result, Spot spot) {
        nextSpots.put(result, spot);
        if (result == YutResult.DO) {
            spot.setPrevSpot(this);
        }
    }

    public Spot getPrevSpot() {
        return prevSpot;
    }

    public void setPrevSpot(Spot prevSpot) {
        this.prevSpot = prevSpot;
    }

    /**
     * Yut 결과에 따른 다음 Spot 계산
     */
    public Spot getNextSpot(YutResult result) {
        if (nextSpots.containsKey(result)) {
            return nextSpots.get(result);
        }
        if (result == YutResult.BACKDO) {
            return prevSpot;
        }
        if (nextSpots.containsKey(YutResult.DO)) {
            Spot next = nextSpots.get(YutResult.DO);
            YutResult remaining;
            switch (result) {
                case DO:
                    return next;
                case GAE:
                    remaining = YutResult.DO;
                    break;
                case GEOL:
                    remaining = YutResult.GAE;
                    break;
                case YUT:
                    remaining = YutResult.GEOL;
                    break;
                case MO:
                    remaining = YutResult.YUT;
                    break;
                default:
                    return null;
            }
            return next.getNextSpot(remaining);
        }
        return null;
    }

    /**
     * 지름길(Path) 연결 추가
     */
    public void addNextPath(YutResult result, Path path) {
        nextPaths.put(result, path);
    }

    public Path getNextPath(YutResult result) {
        return nextPaths.get(result);
    }

    public boolean hasPath(YutResult result) {
        return nextPaths.containsKey(result);
    }

    @Override
    public String toString() {
        return "Spot{id=" + id + "}";
    }
}
