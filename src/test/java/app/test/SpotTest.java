package app.test;

import app.Model.Enum.YutResult;
import app.Model.Path;
import app.Model.Spot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpotTest {

    private Spot s0, s1, s2, s3;
    private Path shortcut;

    @BeforeEach
    void setUp() {
        // create spots with id and flags
        s0 = new Spot(0, false, true, false);
        s1 = new Spot(1, false, false, false);
        s2 = new Spot(2, false, false, false);
        s3 = new Spot(3, false, false, true);
        // create a dummy shortcut path
        shortcut = new Path("shortcut", true);
    }

    @Test
    void testIdAndFlags() {
        assertEquals(0, s0.getId());
        assertTrue(s0.isStart());
        assertFalse(s0.isCorner());
        assertFalse(s0.isFinish());

        assertEquals(3, s3.getId());
        assertTrue(s3.isFinish());
    }

    @Test
    void testAddNextSpotAndPrevSpot() {
        // link s0 → s1 with DO
        s0.addNextSpot(YutResult.DO, s1);
        assertSame(s1, s0.getNextSpot(YutResult.DO), "DO should go to s1");
        // prevSpot should be set on s1
        assertSame(s0, s1.getPrevSpot(), "s1.prevSpot should be s0");

        // link s1 → s2 with GAE (should not set prevSpot)
        s1.addNextSpot(YutResult.GAE, s2);
        assertSame(s2, s1.getNextSpot(YutResult.GAE), "GAE should go to s2");
        assertNotEquals(s1, s2.getPrevSpot(), "GAE should not set prevSpot");
    }

    @Test
    void testGetNextSpotChaining_DO_GAE_GEOL() {
        // build a simple linear chain via DO
        s0.addNextSpot(YutResult.DO, s1);
        s1.addNextSpot(YutResult.DO, s2);
        s2.addNextSpot(YutResult.DO, s3);

        // DO from s0 → s1
        assertSame(s1, s0.getNextSpot(YutResult.DO));

        // GAE (2) from s0 → s2
        assertSame(s2, s0.getNextSpot(YutResult.GAE));

        // GEOL (3) from s0 → s3
        assertSame(s3, s0.getNextSpot(YutResult.GEOL));

        // YUT (4) from s0 → null (overshoot beyond chain)
        assertNull(s0.getNextSpot(YutResult.YUT));
    }

    @Test
    void testBackdo() {
        // build DO chain s0 → s1 → s2
        s0.addNextSpot(YutResult.DO, s1);
        s1.addNextSpot(YutResult.DO, s2);

        // BACKDO from s2 returns prevSpot s1
        assertSame(s1, s2.getNextSpot(YutResult.BACKDO));

        // BACKDO from s0 (start) returns null
        assertNull(s0.getNextSpot(YutResult.BACKDO));
    }

    @Test
    void testAddAndGetNextPath() {
        assertFalse(s0.hasPath(YutResult.YUT), "Initially no paths");
        s0.addNextPath(YutResult.YUT, shortcut);
        assertTrue(s0.hasPath(YutResult.YUT), "hasPath should be true after adding");
        assertSame(shortcut, s0.getNextPath(YutResult.YUT), "getNextPath should return the added path");
        // other results should still be null
        assertNull(s0.getNextPath(YutResult.DO), "No DO path should be present");
    }

    @Test
    void testGetNextSpotWithOnlyPathEntry() {
        // if a shortcut entry exists, getNextSpot should return entry
        s0.addNextPath(YutResult.YUT, shortcut);
        // calling getNextSpot for YUT should still go through DO-based default:
        // but since nextSpots is empty, it falls back to null
        assertNull(s0.getNextSpot(YutResult.YUT));
    }

    @Test
    void testToString() {
        assertEquals("Spot{id=0}", s0.toString());
    }
}
