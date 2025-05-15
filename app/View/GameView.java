package app.View;

import app.Model.Board;
import app.Model.Enum.YutResult;
import app.Model.Event.GameEventListener;
import app.Model.Player.Player;

import java.util.List;

public interface GameView extends GameEventListener {
    void initialize();

    void updateBoard(Board board);

    void updatePlayers(List<Player> players);

    void showYutResult(YutResult result);

    void showGameResult(Player winner);

    void showSetupDialog();

    void close();
}