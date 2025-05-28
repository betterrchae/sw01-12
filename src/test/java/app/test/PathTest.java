package app.test;

import app.Model.Path;
import app.Model.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    private Spot s0, s1, s2;
    private Path mainPath;
    private Path shortcutPath;

    @BeforeEach
    void setUp() {
        // create dummy spots with positions
        s0 = new Spot(0, false, true, false);
        s1 = new Spot(1, false, false, false);
        s2 = new Spot(2, false, false, true);

        mainPath = new Path("main", false);
        shortcutPath = new Path("shortcut", true);
    }

    @Test
    void testNameAndShortcutFlag() {
        assertEquals("main", mainPath.getName(), "Path name should match constructor");
        assertFalse(mainPath.isShortcut(), "main path should not be marked as shortcut");

        assertEquals("shortcut", shortcutPath.getName(), "Path name should match constructor");
        assertTrue(shortcutPath.isShortcut(), "shortcut path should be marked as shortcut");
    }

    @Test
    void testAddSpotAndPrevSpotLinking() {
        mainPath.addSpot(s0);
        mainPath.addSpot(s1);
        mainPath.addSpot(s2);

        // after adding, getSpots returns [s0, s1, s2]
        List<Spot> list = mainPath.getSpots();
        assertEquals(3, list.size(), "Should contain three spots");
        assertSame(s0, list.get(0));
        assertSame(s1, list.get(1));
        assertSame(s2, list.get(2));

        // prevSpot linking
        assertNull(s0.getPrevSpot(), "First spot should have no prevSpot");
        assertSame(s0, s1.getPrevSpot(), "Second spot's prevSpot should be first spot");
        assertSame(s1, s2.getPrevSpot(), "Third spot's prevSpot should be second spot");
    }

    @Test
    void testGetLastSpotEmptyAndNonEmpty() {
        assertNull(mainPath.getLastSpot(), "Empty path should return null for last spot");

        mainPath.addSpot(s0);
        assertSame(s0, mainPath.getLastSpot(), "Last spot should be the only spot");

        mainPath.addSpot(s1);
        assertSame(s1, mainPath.getLastSpot(), "Last spot should update when adding new spots");
    }

    @Test
    void testGetSpotAfterMoveValid() {
        mainPath.addSpot(s0);
        mainPath.addSpot(s1);
        mainPath.addSpot(s2);

        // move from s0 by 1 -> s1
        assertSame(s1, mainPath.getSpotAfterMove(s0, 1));

        // move from s0 by 2 -> s2
        assertSame(s2, mainPath.getSpotAfterMove(s0, 2));

        // move from s1 by 1 -> s2
        assertSame(s2, mainPath.getSpotAfterMove(s1, 1));
    }

    @Test
    void testGetSpotAfterMoveInvalid() {
        mainPath.addSpot(s0);
        mainPath.addSpot(s1);

        // moving beyond end returns null
        assertNull(mainPath.getSpotAfterMove(s0, 5), "Overshoot should return null");

        // currentSpot not in path returns null
        assertNull(mainPath.getSpotAfterMove(s2, 1), "Unknown currentSpot should return null");

        // empty path always returns null
        Path empty = new Path("empty", false);
        assertNull(empty.getSpotAfterMove(s0, 1), "Empty path should return null");
    }

    @Test
    void testGetSpotsUnmodifiable() {
        mainPath.addSpot(s0);
        List<Spot> spotsView = mainPath.getSpots();
        assertThrows(UnsupportedOperationException.class,
                () -> spotsView.add(s1),
                "getSpots() should return an unmodifiable list");
    }
}
