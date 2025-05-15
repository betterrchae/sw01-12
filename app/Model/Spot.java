package app.Model;

import app.Model.Enum.YutResult;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Spot {
    private final int id;
    private final Point position;
    private final boolean isCorner;
    private final boolean isStart;
    private final boolean isFinish;
    private final Map<YutResult, Spot> nextSpots;
    private final Map<YutResult, Path> nextPaths;
    private Spot prevSpot;

    public Spot(int id, Point position, boolean isCorner, boolean isStart, boolean isFinish) {
        this.id = id;
        this.position = position;
        this.isCorner = isCorner;
        this.isStart = isStart;
        this.isFinish = isFinish;
        this.nextSpots = new HashMap<>();
        this.nextPaths = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public Point getPosition() {
        return position;
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

    public void addNextSpot(YutResult result, Spot spot) {
        nextSpots.put(result, spot);

        if (result == YutResult.DO) {
            spot.setPrevSpot(this);
        }
    }

    public Spot getPrevSpot() {
        return prevSpot;
    }

    public void setPrevSpot(Spot spot) {
        this.prevSpot = spot;
    }

    public Spot getNextSpot(YutResult result) {
        if (nextSpots.containsKey(result)) {
            return nextSpots.get(result);
        }

        // 빽도인 경우 이전 칸 반환
        if (result == YutResult.BACKDO) {
            return prevSpot;
        }

        // 기본 다음 칸 반환
        if (nextSpots.containsKey(YutResult.DO)) {
            Spot next = nextSpots.get(YutResult.DO);
            YutResult remaining;

            switch (result) {
                case DO:
                    // DO는 바로 다음 칸이므로 추가 이동 없음
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
                    // 처리되지 않은 경우
                    return null;
            }

            return next.getNextSpot(remaining);
        }

        return null;
    }

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