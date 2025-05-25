package app.test;

import app.Model.Horse.Horse;
import app.Model.Horse.HorseGroup;
import app.Model.Player.Player;
import app.Model.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HorseGroupTest {

    private Player owner;
    private Horse h1;
    private Horse h2;
    private HorseGroup group;
    private Spot normalSpot;
    private Spot finishSpot;

    @BeforeEach
    void setUp() {
        owner = new Player("Tester", Color.BLUE, 2);
        h1 = new Horse(1, owner);
        h2 = new Horse(2, owner);
        group = new HorseGroup(h1);
        normalSpot = new Spot(10, false, false, false);
        finishSpot = new Spot(99, false, false, true);
    }

    @Test
    void constructorAddsInitialHorse() {
        List<Horse> horses = group.getHorses();
        assertEquals(1, horses.size(), "초기화 시 한 마리가 그룹에 있어야 한다");
        assertTrue(horses.contains(h1), "그룹에 initialHorse가 포함되어야 한다");
        assertEquals(group, h1.getGroup(), "initialHorse의 group 참조가 설정되어야 한다");
    }

    @Test
    void addHorseAddsNewHorse() {
        group.addHorse(h2);
        List<Horse> horses = group.getHorses();
        assertEquals(2, horses.size(), "두 마리가 그룹에 있어야 한다");
        assertTrue(horses.contains(h1) && horses.contains(h2));
        assertEquals(group, h2.getGroup(), "추가된 말의 group 참조가 설정되어야 한다");
    }

    @Test
    void addHorseNullOrDuplicateDoesNothing() {
        group.addHorse(null);
        assertEquals(1, group.getHorses().size());
        group.addHorse(h1); // 중복 추가 시도
        assertEquals(1, group.getHorses().size(), "중복된 말은 추가되지 않아야 한다");
    }

    @Test
    void removeHorseRemovesAndClearsGroupWhenOneLeft() {
        // 두 마리 추가 후 하나 제거 → 남은 한 마리까지 클리어
        group.addHorse(h2);
        group.removeHorse(h1);
        // 그룹 내 남은 마리가 하나일 때 자동 해제되므로 리스트가 비워져야 함
        assertTrue(group.getHorses().isEmpty(), "하나만 남으면 그룹이 해제되어야 한다");
        assertNull(h2.getGroup(), "마지막 말의 group 참조가 null이어야 한다");
    }

    @Test
    void removeHorseWhenNotInGroupDoesNothing() {
        group.removeHorse(h2); // h2는 아예 그룹에 없음
        assertEquals(1, group.getHorses().size(), "그룹에 없는 말을 제거해도 변화 없어야 한다");
    }

    @Test
    void moveSetsAllHorsesToDestinationAndMarksFinished() {
        group.addHorse(h2);
        // move to normal spot
        boolean moved1 = group.move(normalSpot);
        assertTrue(moved1);
        assertEquals(normalSpot, h1.getCurrentSpot());
        assertEquals(normalSpot, h2.getCurrentSpot());
        assertFalse(h1.isFinished());
        assertFalse(h2.isFinished());

        // move to finish spot
        boolean moved2 = group.move(finishSpot);
        assertTrue(moved2);
        assertEquals(finishSpot, h1.getCurrentSpot());
        assertEquals(finishSpot, h2.getCurrentSpot());
        assertTrue(h1.isFinished(), "도착 칸으로 이동 시 말이 완료 처리되어야 한다");
        assertTrue(h2.isFinished());
    }

    @Test
    void getHorsesReturnsUnmodifiableList() {
        List<Horse> list = group.getHorses();
        assertThrows(UnsupportedOperationException.class, () -> list.add(h2), "getHorses()로 반환된 리스트는 수정 불가능해야 한다");
    }
}