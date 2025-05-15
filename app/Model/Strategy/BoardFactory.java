package app.Model.Strategy;

import app.Model.Board;
import app.Model.Enum.BoardType;

public class BoardFactory {
    public static Board createBoard(BoardType type) {
        BoardLayoutStrategy strategy;
        switch (type) {
            case SQUARE:
                strategy = new SquareBoardLayoutStrategy();
                break;
            case PENTAGON:
                strategy = new PentagonBoardLayoutStrategy();
                break;
            case HEXAGON:
                strategy = new HexagonBoardLayoutStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unsupported board type: " + type);
        }
        return strategy.createBoard();
    }
}
