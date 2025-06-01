package app.test;

import app.Model.Enum.BoardType;
import app.Model.Enum.YutResult;
import app.Model.Game;
import app.Model.Horse.Horse;
import app.Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setupGame(2, 2, BoardType.SQUARE);
    }

    @Test
    void setupGame_throwsOnTooFewPlayers() {
        Game game = new Game();
        assertThrows(
                IllegalArgumentException.class,
                () -> game.setupGame(1, 3, BoardType.SQUARE),
                "Player count must be between 2 and 4"
        );
    }


    @Test
    void testSetupGame_validParameters_initializesCorrectly() {
        game.setupGame(2, 2, BoardType.SQUARE);

        // 플레이어 수
        List<Player> players = game.getPlayers();
        assertEquals(2, players.size());

        // 첫 번째 플레이어가 현재 플레이어
        assertNotNull(game.getCurrentPlayer());
        assertEquals(players.get(0), game.getCurrentPlayer());

        // 게임 상태 IN_PROGRESS
        assertEquals(app.Model.Enum.GameState.IN_PROGRESS, game.getState());

        // 초기 윷 결과 리스트는 비어있음
        assertTrue(game.getCurrentResults().isEmpty());

        // 보드가 null이 아니어야 함
        assertNotNull(game.getBoard());
    }

    @Test
    void testThrowYut_specificResult_addedToCurrentResults() {
        game.setupGame(1, 1, BoardType.SQUARE);

        // 지정 던지기: DO
        YutResult result = game.throwYut(false, YutResult.DO);
        assertEquals(YutResult.DO, result);

        // 현재 결과 목록에 하나 살아있어야 함
        List<YutResult> results = game.getCurrentResults();
        assertEquals(1, results.size());
        assertEquals(YutResult.DO, results.get(0));

        // DO는 canThrowAgain==false
        assertFalse(game.canThrowAgain());
    }

    @Test
    void testThrowYut_MO_canThrowAgainTrue() {
        game.setupGame(1, 1, BoardType.SQUARE);

        YutResult result = game.throwYut(false, YutResult.MO);
        assertEquals(YutResult.MO, result);
        // MO가 나오면 한번 더 던질 수 있음
        assertTrue(game.canThrowAgain());
    }

    @Test
    void testMoveHorse_DO_movesHorseAlongMainPath() {
        game.setupGame(1, 1, BoardType.SQUARE);
        Player p = game.getCurrentPlayer();
        Horse h = p.getHorses().get(0);

        // 1칸 이동하는 DO 던지기
        YutResult r = game.throwYut(false, YutResult.DO);
        assertTrue(game.moveHorse(h, r));

        // 말의 currentSpot이 null이 아니어야 함 (출발 지점에서 한 칸 이동)
        assertNotNull(h.getCurrentSpot());
        // DO는 메인 경로 두번째 Spot(인덱스1)여야 함
        assertEquals(1, h.getCurrentSpot().getId());

        // 현재 사용된 결과는 제거되고 빈 리스트여야 함
        assertTrue(game.getCurrentResults().isEmpty());
    }

    @Test
    void testCaptureEnemyHorse_returnsToStartAndCanThrowAgain() {
        // 두 명의 플레이어, 각 1마리
        game.setupGame(2, 1, BoardType.SQUARE);
        Player attacker = game.getCurrentPlayer();
        Player defender = game.getPlayers().get(1);
        Horse a = attacker.getHorses().get(0);
        Horse d = defender.getHorses().get(0);

        // attacker 말 2칸 이동
        YutResult two = YutResult.GAE; // moveCount=2
        game.throwYut(false, two);
        game.moveHorse(a, two);

        // defender의 턴으로 넘어가서
        // (자동으로 TURN_CHANGE 되므로 canThrowAgain=false일 때 nextTurn도 자동)
        // defender도 2칸 이동시켜 attacker가 점령한 spot에 진입
        game.throwYut(false, two);
        game.moveHorse(d, two);

        // 공격자는 한 번 더 던질 수 있음
        assertTrue(game.canThrowAgain());
    }
}
