package app.Controller;

import java.util.List;

import javax.swing.*;

import app.Model.Enum.BoardType;
import app.Model.Enum.GameEventType;
import app.Model.Enum.YutResult;
import app.Model.Game;
import app.Model.Horse;
import app.View.GameView;
import app.View.SwingGameView;

public class GameController {
  private final Game game;
  private final GameView view;

  public GameController(Game game, GameView view) {
    this.game = game;
    this.view = view;

    // SwingGameView에서 컨트롤러 참조 설정 (다른 뷰 구현체도 필요시)
    if (view instanceof SwingGameView) {
      ((SwingGameView) view).setController(this);
    }

    // 게임 이벤트 리스너 등록
    for (GameEventType type : GameEventType.values()) {
      game.addEventListener(type, view);
    }
  }

  public Game getGame() {
    return game;
  }

  public void setupGame(int playerCount, int horseCount, BoardType boardType) {
    game.setupGame(playerCount, horseCount, boardType);
    view.updateBoard(game.getBoard());
    view.updatePlayers(game.getPlayers());
  }

  public void handleYutThrow(boolean isRandom, YutResult specificResult) {
    YutResult result = game.throwYut(isRandom, specificResult);
    if (result != null) {
      // 화면에는 이미 이벤트를 통해 표시됨
    }
  }

  public void handleHorseSelection(Horse horse) {
    // 선택한 말에 대해 사용 가능한 윷 결과 확인
    List<YutResult> availableResults = game.getCurrentResults();
    if (availableResults.isEmpty()) {
      return;
    }

    // 윷 결과 선택 다이얼로그 표시
    if (view instanceof SwingGameView) {
      JComboBox<YutResult> resultCombo = new JComboBox<>(
          availableResults.toArray(new YutResult[0]));

      int option = JOptionPane.showConfirmDialog(null,
          new Object[] { "이동할 윷 결과를 선택하세요:", resultCombo },
          "윷 선택", JOptionPane.OK_CANCEL_OPTION);

      if (option == JOptionPane.OK_OPTION) {
        YutResult selectedResult = (YutResult) resultCombo.getSelectedItem();
        game.moveHorse(horse, selectedResult);
      }
    } else {
      // 다른 UI 구현체에 대한 처리
      if (!availableResults.isEmpty()) {
        game.moveHorse(horse, availableResults.get(0));
      }
    }
  }

  public void handleRestartGame() {
    game.restart();
    view.showSetupDialog();
  }

  public void handleExitGame() {
    view.close();
    System.exit(0);
  }

  public void initializeGame() {
    // 뷰 초기화
    view.initialize();

    // 게임 이벤트 리스너 등록
    for (GameEventType type : GameEventType.values()) {
      game.addEventListener(type, view);
    }

    // 게임 설정 다이얼로그 표시
    view.showSetupDialog();
  }
}