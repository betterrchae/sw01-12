package app.Model.Strategy;

import app.Model.Board;
import app.Model.Enum.BoardType;

/**
 * 보드 타입에 맞는 레이아웃 전략을 선택하고
 * 생성된 LayoutResult에서 Board 객체를 반환합니다.
 */
public class BoardFactory {
    public static Board createBoard(BoardType type) {
        BoardLayoutStrategy strategy;
        switch (type) {
            case SQUARE:
                strategy = new SquareBoardLayoutStrategy();
                break;
//            case PENTAGON:
//                strategy = new PentagonBoardLayoutStrategy();
//                break;
//            case HEXAGON:
//                strategy = new HexagonBoardLayoutStrategy();
//                break;
            default:
                throw new IllegalArgumentException("Unsupported board type: " + type);
        }
        // 레이아웃 결과에서 Board 객체만 추출
        return strategy.createLayout().getBoard();
    }
}
