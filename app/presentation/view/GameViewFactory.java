package app.presentation.view;

public class GameViewFactory {
    public static GameView createGameView(String uiType) {
        if (uiType.equalsIgnoreCase("swing")) {
            return new SwingGameView();
        }
        throw new IllegalArgumentException("Unsupported UI type: " + uiType);
    }
}