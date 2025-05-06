package app.View;

import app.Model.Enum.BoardType;
import app.Model.Board;
import app.Model.Strategy.SquareBoardLayoutStrategy;
import app.Model.Strategy.HexagonBoardLayoutStrategy;
import app.Model.Strategy.PentagonBoardLayoutStrategy;

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
