package app.presentation.view;

import javafx.stage.Stage;

public class GameViewFactory {
    public static GameView createGameView(String uiType) {
        return switch (uiType.toLowerCase()) {
            case "swing" -> new SwingGameView();
            case "javafx" -> {
                Stage fxStage = new Stage();
                yield new JavaFXGameView(fxStage);
            }
            default -> throw new IllegalArgumentException("Unknown UI type: " + uiType);
        };
    }
}
