package app;

import app.Controller.GameController;
import app.Model.Game;
import app.presentation.view.GameView;
import app.presentation.view.GameViewFactory;
import javafx.application.Platform;

import javax.swing.SwingUtilities;
import java.util.Scanner;

public class YutGameApplication {
    public static void main(String[] args) {
        // UI 타입 설정 (기본값: Swing)
        Scanner scanner = new Scanner(System.in);

        System.out.print("UI 타입을 입력하세요 (swing 또는 javafx): ");
        String uiType = scanner.nextLine().trim().toLowerCase();

        if ("swing".equalsIgnoreCase(uiType)) {
            SwingUtilities.invokeLater(() -> {
                // 모델·뷰·컨트롤러 생성
                Game game = new Game();
                GameView view = GameViewFactory.createGameView(uiType);
                GameController controller = new GameController(game, view);

                // *** 반드시 EDT 위에서 호출해야 다이얼로그가 보여집니다! ***
                controller.initializeGame();
            });
        }

        if ("javafx".equalsIgnoreCase(uiType)) {
            Platform.startup(() -> {
                Game game = new Game();
                GameView view = GameViewFactory.createGameView(uiType);
                GameController controller = new GameController(game, view);
                controller.initializeGame();
            });
        }
        scanner.close();
    }
}
