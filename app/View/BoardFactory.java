package app.View;

import app.Model.Enum.BoardType;
import app.Model.Board;
import app.Model.Strategy.SquareBoardLayoutStrategy;

import java.util.Objects;

public class BoardFactory {
  public static Board createBoard(BoardType type) {
    BoardLayoutStrategy strategy;
      if (Objects.requireNonNull(type) == BoardType.SQUARE) {
          strategy = new SquareBoardLayoutStrategy();
      } else {
          throw new IllegalArgumentException("Unsupported board type: " + type);
      }
    return strategy.createBoard();
  }
}
