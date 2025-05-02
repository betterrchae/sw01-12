package View.Code;

import View.Code.Controller.GameController;
import View.Code.Model.Game;
import View.Code.View.GameView;
import View.Code.View.GameViewFactory;

public class YutGameApplication {
  public static void main(String[] args) {
    // UI 타입 설정 (기본값: Swing)
    String uiType = "swing";

    try {
      // 게임 모델, 뷰, 컨트롤러 생성
      Game game = new Game();
      GameView view = GameViewFactory.createGameView(uiType);
      GameController controller = new GameController(game, view);

      // 컨트롤러를 통해 게임 초기화 및 시작
      controller.initializeGame();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("게임 시작 중 오류가 발생했습니다: " + e.getMessage());
      System.exit(1);
    }
  }
}