package app.Controller;

import app.Model.Enum.BoardType;
import app.Model.Enum.GameEventType;
import app.Model.Enum.YutResult;
import app.Model.Game;
import app.Model.Horse.Horse;
import app.presentation.view.GameView;
import app.presentation.view.SwingGameView;

import java.util.List;

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
            view.showNotification("이미 윷을 던졌습니다. 말을 이동시켜야 합니다.");
        }
    }

    public void handleHorseSelection(Horse horse) {
        // 1) 사용 가능한 윷 결과 리스트 조회
        List<YutResult> available = game.getCurrentResults();
        if (available.isEmpty()) {
            // (옵션) 선택지 자체가 없으면 사용자에게 알림
            view.showNotification("사용 가능한 윷 결과가 없습니다.");
            return;
        }

        // 2) View에 다이얼로그 호출을 위임
        YutResult selected = view.promptYutSelection(available);
        if (selected == null) {
            // 취소했으면 아무 작업도 하지 않음
            return;
        }

        // 3) 선택된 결과로 말 이동
        boolean moved = game.moveHorse(horse, selected);

        // 4) 화면 갱신
        view.updateBoard(game.getBoard());
        view.updatePlayers(game.getPlayers());

        // 5) 추가 던지기 가능 시 상태 초기화
        if (moved && game.getCurrentResults().isEmpty() && game.canThrowAgain()) {
            hasThrownYut = false;
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

        // 게임 설정 다이얼로그 표시
        view.showSetupDialog();
    }
}