package View.Code.View;

import View.Code.Enum.BoardType;
import View.Code.Model.Board;
import View.Code.Strategy.SquareBoardLayoutStrategy;

public class BoardFactory {
  public static Board createBoard(BoardType type) {
    BoardLayoutStrategy strategy;
    switch (type) {
      case SQUARE:
        strategy = new SquareBoardLayoutStrategy();
        break;
      // case PENTAGON:
      // strategy = new PentagonBoardLayoutStrategy();
      // break;
      // case HEXAGON:
      // strategy = new HexagonBoardLayoutStrategy();
      // break;
      default:
        throw new IllegalArgumentException("Unsupported board type: " + type);
    }
    return strategy.createBoard();
  }
}
