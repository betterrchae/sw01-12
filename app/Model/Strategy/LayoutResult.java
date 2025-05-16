package app.Model.Strategy;

import app.Model.Board;
import app.Model.Line;
import app.Model.Spot;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Board + Spot→Point 매핑 + Line 목록을 한번에 담아 반환하는 DTO
 */
public class LayoutResult {
    public final Board board;
    public final Map<Spot, Point> spotPositions;
    public final List<Line> lines;

    public LayoutResult(Board board, Map<Spot, Point> spotPositions, List<Line> lines) {
        this.board = board;
        this.spotPositions = spotPositions;
        this.lines = lines;
    }

    public Board getBoard() {
        return board;
    }
}
