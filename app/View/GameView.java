package View.Code.View;

import java.util.List;

import View.Code.Enum.YutResult;
import View.Code.Event.GameEventListener;
import View.Code.Model.Board;
import View.Code.Model.Player;

public interface GameView extends GameEventListener {
  void initialize();

  void updateBoard(Board board);

  void updatePlayers(List<Player> players);

  void showYutResult(YutResult result);

  void showGameResult(Player winner);

  void showSetupDialog();

  void close();
}