package app.Controller;

import java.util.List;

import javax.swing.*;

import app.Model.Enum.BoardType;
import app.Model.Enum.GameEventType;
import app.Model.Enum.YutResult;
import app.Model.Game;
import app.Model.Horse.Horse;
import app.View.GameView;
import app.View.SwingGameView;

public class GameController {
  private final Game game;
  private final GameView view;
  private boolean hasThrownYut; // 윷을 던졌는지 여부

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

    // 턴 변경 이벤트 리스너 추가 (hasThrownYut 초기화)
    game.addEventListener(GameEventType.TURN_CHANGE, event -> hasThrownYut = false);
  }

  public Game getGame() {
    return game;
  }

  public void setupGame(int playerCount, int horseCount, BoardType boardType) {
    game.setupGame(playerCount, horseCount, boardType);
    view.updateBoard(game.getBoard());
    view.updatePlayers(game.getPlayers());
    hasThrownYut = false; // 새 게임 시작시 초기화
  }

  public void handleYutThrow(boolean isRandom, YutResult specificResult) {
    // 윷이나 모가 나와서 한 번 더 던질 수 있는 경우 또는 아직 윷을 던지지 않은 경우만 던지기 가능
    if (game.canThrowAgain() || !hasThrownYut) {
      YutResult result = game.throwYut(isRandom, specificResult);
      if (result != null) {
        // 윷을 던졌음을 표시
        hasThrownYut = true;

        // 윷이나 모가 나오면 canThrowAgain이 true로 설정됨
        // 빽도로 인해 턴이 넘어가면 hasThrownYut이 자동으로 false로 초기화됨
      }
    } else {
      // 이미 윷을 던졌고 윷/모가 아니면 말을 이동해야 함을 알림
      JOptionPane.showMessageDialog(null,
          "이미 윷을 던졌습니다. 말을 이동시켜야 합니다.",
          "알림", JOptionPane.INFORMATION_MESSAGE);
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
        boolean moved = game.moveHorse(horse, selectedResult);

        // 말이 이동했고, 그 후에도 사용 가능한 윷 결과가 있으면 hasThrownYut 초기화
        // (말 잡기나 윷/모가 나와 추가 던지기가 가능한 경우)
        if (moved && game.getCurrentResults().isEmpty() && game.canThrowAgain()) {
          hasThrownYut = false;
        }
      }
    } else {
      // 다른 UI 구현체에 대한 처리
      if (!availableResults.isEmpty()) {
        boolean moved = game.moveHorse(horse, availableResults.get(0));

        // 말이 이동했고, 그 후에도 사용 가능한 윷 결과가 있으면 hasThrownYut 초기화
        if (moved && game.getCurrentResults().isEmpty() && game.canThrowAgain()) {
          hasThrownYut = false;
        }
      }
    }
  }

  public void handleRestartGame() {
    game.restart();
    hasThrownYut = false;
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