package app.test;

import app.Model.Board;
import app.Model.Enum.BoardType;
import app.Model.Enum.YutResult;
import app.Model.Horse.Horse;
import app.Model.Line;
import app.Model.Path;
import app.Model.Player.Player;
import app.Model.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Spot s0, s1, s2;
    private Board board;
    private Horse horse;
    private Player player;

    @BeforeEach
    void setUp() {
        // 1) Spot 생성 (이제 position은 빼고 id, corner/start/finish 플래그만)
        s0 = new Spot(0, false, true, false);   // 시작 칸
        s1 = new Spot(1, false, false, false);  // 중간 칸
        s2 = new Spot(2, false, false, true);   // 도착 칸

        // 2) DO 이동 연결
        s0.addNextSpot(YutResult.DO, s1);
        s1.addNextSpot(YutResult.DO, s2);

        // 3) 간단한 보드 생성: spots, lines, paths
        List<Spot> spots = Arrays.asList(s0, s1, s2);
        List<Line> lines = Collections.emptyList();   // 선은 테스트 대상 아님
        List<Path> paths = Collections.emptyList();   // 경로도 테스트 대상 아님

        // 4) Spot → 좌표 맵 직접 작성
        Map<Spot, Point> spotPositions = new HashMap<>();
        spotPositions.put(s0, new Point(0, 0));
        spotPositions.put(s1, new Point(10, 0));
        spotPositions.put(s2, new Point(20, 0));

        board = new Board(BoardType.SQUARE, spots, lines, paths, spotPositions);

        // 5) 말과 플레이어 준비
        player = new Player("P", Color.BLACK, 1);
        horse = new Horse(0, player);
    }

    @Test
    void testGetSpotPosition() {
        // spotPositions 맵에서 제대로 꺼내오는지 확인
        assertEquals(new Point(10, 0), board.getSpotPosition(s1));
    }

    @Test
    void testUpdateAndGetHorsesAtSpot() {
        // 말을 s1에 배치
        horse.setCurrentSpot(s1);
        board.updateHorsePosition(horse);

        // s1에 말이 한 개 있어야 함
        List<Horse> atS1 = board.getHorsesAtSpot(s1);
        assertEquals(1, atS1.size());
        assertEquals(horse, atS1.get(0));

        // 말을 완주 처리하면 보드에서 제거되는지 확인
        horse.setFinished(true);
        board.updateHorsePosition(horse);
        assertTrue(board.getHorsesAtSpot(s1).isEmpty());
    }

    @Test
    void testCalculateNextSpot_DO_and_BACKDO() {
        // DO 이동: s1 → s2
        Spot nextFromS1 = board.calculateNextSpot(horse, s1, YutResult.DO);
        assertEquals(s2, nextFromS1);

        // BACKDO 이동: s1 → s0
        Spot backFromS1 = board.calculateNextSpot(horse, s1, YutResult.BACKDO);
        assertEquals(s0, backFromS1);

        // BACKDO 이동: s0은 시작 칸이므로 prevSpot이 null
        assertNull(board.calculateNextSpot(horse, s0, YutResult.BACKDO));
    }

    @Test
    void testMoveAlongMainPath_multipleSteps() {
        // s0에서 GAE(2) 이동: s0 → s2
        Spot twoSteps = board.calculateNextSpot(horse, s0, YutResult.GAE);
        assertEquals(s2, twoSteps);

        // s1에서 GAE(2) 이동: 첫 DO로 s2, 두 번째 DO는 다음이 없으므로 finishSpot 리턴
        Spot overshoot = board.calculateNextSpot(horse, s1, YutResult.GAE);
        assertEquals(s2, overshoot);
    }
}
