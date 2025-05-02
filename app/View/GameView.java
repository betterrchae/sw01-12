package app.View;

import java.util.List;

import app.Model.Enum.YutResult;
import app.Model.Event.GameEventListener;
import app.Model.Board;
import app.Model.Player;

public interface GameView extends GameEventListener {
  void initialize();

  void updateBoard(Board board);

  void updatePlayers(List<Player> players);

  void showYutResult(YutResult result);

  void showGameResult(Player winner);

  void showSetupDialog();

  void close();
}