package View.Code.View;

public class GameViewFactory {
  public static GameView createGameView(String uiType) {
    switch (uiType.toLowerCase()) {
      case "swing":
        return new SwingGameView();
      // case "javafx":
      // return new JavaFXGameView();
      // case "swt":
      // return new SWTGameView();
      default:
        throw new IllegalArgumentException("Unsupported UI type: " + uiType);
    }
  }
}