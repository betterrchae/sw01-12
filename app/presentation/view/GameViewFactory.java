package app.presentation.view;

public class GameViewFactory {
    public static GameView createGameView(String uiType) {
        switch (uiType.toLowerCase()) {
            case "swing":
                return new SwingGameView();
            case "javafx":
//                return new JavaFXGameView(fxStage);
            default:
                throw new IllegalArgumentException("Unknown UI type: " + uiType);
        }
    }
}