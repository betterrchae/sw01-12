package app.test;


import app.Model.Horse.Horse;
import app.Model.Horse.HorseGroup;
import app.Model.Player.Player;
import app.Model.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    private Player dummyOwner;
    private Horse horse;
    private Spot spotA;
    private Spot spotFinish;

    @BeforeEach
    void setUp() {
        // 플레이어, 스팟 초기화
        dummyOwner = new Player("Tester", Color.MAGENTA, 1);
        horse = new Horse(7, dummyOwner);
        spotA = new Spot(1, false, false, false);
        spotFinish = new Spot(99, false, false, true);
    }

    @Test
    void testInitialState() {
        assertEquals(7, horse.getId());
        assertEquals(dummyOwner, horse.getOwner());
        assertNull(horse.getCurrentSpot(), "초기 currentSpot은 null이어야 한다");
        assertFalse(horse.isFinished(), "초기 isFinished는 false");
        assertFalse(horse.isInGroup(), "초기 isInGroup은 false");
    }

    @Test
    void testMoveUpdatesSpot() {
        boolean moved = horse.move(spotA);
        assertTrue(moved, "move()는 정상 이동 시 true");
        assertEquals(spotA, horse.getCurrentSpot());
        assertFalse(horse.isFinished(), "finish 칸이 아니면 isFinished는 여전히 false");
    }

    @Test
    void testMoveToFinishSpotSetsFinished() {
        boolean moved = horse.move(spotFinish);
        assertTrue(moved);
        assertEquals(spotFinish, horse.getCurrentSpot());
        assertTrue(horse.isFinished(), "도착 칸으로 이동 시 isFinished가 true");
    }

    @Test
    void testCannotMoveWhenAlreadyFinished() {
        horse.setFinished(true);
        horse.setCurrentSpot(spotA);
        boolean moved = horse.move(new Spot(2, false, false, false));
        assertFalse(moved, "이미 완료된 말은 move() 시도 시 false");
        assertEquals(spotA, horse.getCurrentSpot(), "위치 변경 없어야 함");
    }

    @Test
    void testCannotMoveWhenInGroup() {
        // 그룹에 속해 있으면 move()는 false 리턴
        HorseGroup group = new HorseGroup(horse);
        horse.setGroup(group);
        boolean moved = horse.move(spotA);
        assertFalse(moved, "그룹에 속한 말은 개별 move() 시 false");
        assertNull(horse.getCurrentSpot(), "currentSpot 변경 없어야 함");
    }

    @Test
    void testToStringContainsIdAndOwnerName() {
        String str = horse.toString();
        assertTrue(str.contains("id=7"), "toString에 id가 포함되어야 함");
        assertTrue(str.contains(dummyOwner.getName()), "toString에 owner 이름이 포함되어야 함");
    }
}
