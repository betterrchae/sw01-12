package app;

import app.Controller.GameController;
import app.Model.Game;
import app.presentation.view.GameView;
import app.presentation.view.GameViewFactory;

import javax.swing.SwingUtilities;

public class YutGameApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // UI 타입 설정 (기본값: Swing)
            String uiType = "swing";

            // 모델·뷰·컨트롤러 생성
            Game game = new Game();
            GameView view = GameViewFactory.createGameView(uiType);
            GameController controller = new GameController(game, view);

            // *** 반드시 EDT 위에서 호출해야 다이얼로그가 보여집니다! ***
            controller.initializeGame();
        });
    }
}
