package app.test;

import app.Model.Line;
import app.Model.Spot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    private Spot spot(int id) {
        return new Spot(id, false, false, false);
    }

    @Test
    void equals_sameFromTo_shouldBeEqual() {
        Spot a = spot(1);
        Spot b = spot(2);
        Line l1 = new Line(a, b);
        Line l2 = new Line(a, b);

        assertEquals(l1, l2, "Lines with the same from/to should be equal");
        assertEquals(l1.hashCode(), l2.hashCode(), "Equal objects must have the same hashCode");
    }

    @Test
    void equals_differentFromOrTo_shouldNotBeEqual() {
        Spot a = spot(1);
        Spot b = spot(2);
        Spot c = spot(3);

        Line l1 = new Line(a, b);
        Line l2 = new Line(b, a);  // reversed
        Line l3 = new Line(a, c);  // different to

        assertNotEquals(l1, l2, "Lines with reversed endpoints should not be equal");
        assertNotEquals(l1, l3, "Lines with different endpoints should not be equal");
    }

    @Test
    void equals_nullOrOtherType_shouldReturnFalse() {
        Spot a = spot(1);
        Spot b = spot(2);
        Line l = new Line(a, b);

        assertNotEquals(l, null, "Line should not be equal to null");
        assertNotEquals(l, "not a line", "Line should not be equal to an object of another type");
    }

    @Test
    void getters_shouldReturnConstructorValues() {
        Spot a = spot(1);
        Spot b = spot(2);
        Line l = new Line(a, b);

        assertSame(a, l.getFrom(), "getFrom() should return the constructor's from value");
        assertSame(b, l.getTo(), "getTo() should return the constructor's to value");
    }
}