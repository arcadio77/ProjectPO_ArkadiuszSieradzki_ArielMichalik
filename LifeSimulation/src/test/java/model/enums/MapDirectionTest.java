package model.enums;

import model.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    void testFromIntegerValid() {
        assertEquals(MapDirection.NORTH, MapDirection.fromInteger(0));
        assertEquals(MapDirection.NORTHEAST, MapDirection.fromInteger(1));
        assertEquals(MapDirection.EAST, MapDirection.fromInteger(2));
        assertEquals(MapDirection.SOUTHEAST, MapDirection.fromInteger(3));
        assertEquals(MapDirection.SOUTH, MapDirection.fromInteger(4));
        assertEquals(MapDirection.SOUTHWEST, MapDirection.fromInteger(5));
        assertEquals(MapDirection.WEST, MapDirection.fromInteger(6));
        assertEquals(MapDirection.NORTHWEST, MapDirection.fromInteger(7));
    }

    @Test
    void testFromIntegerInvalid() {
        assertThrows(IllegalArgumentException.class, () -> MapDirection.fromInteger(-1));
        assertThrows(IllegalArgumentException.class, () -> MapDirection.fromInteger(8));
    }

    @Test
    void testOpposite() {
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH.opposite());
        assertEquals(MapDirection.SOUTHWEST, MapDirection.NORTHEAST.opposite());
        assertEquals(MapDirection.WEST, MapDirection.EAST.opposite());
        assertEquals(MapDirection.NORTHWEST, MapDirection.SOUTHEAST.opposite());
        assertEquals(MapDirection.NORTH, MapDirection.SOUTH.opposite());
        assertEquals(MapDirection.NORTHEAST, MapDirection.SOUTHWEST.opposite());
        assertEquals(MapDirection.EAST, MapDirection.WEST.opposite());
        assertEquals(MapDirection.SOUTHEAST, MapDirection.NORTHWEST.opposite());
    }

    @Test
    void testToUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 1), MapDirection.NORTHEAST.toUnitVector());
        assertEquals(new Vector2d(1, 0), MapDirection.EAST.toUnitVector());
        assertEquals(new Vector2d(1, -1), MapDirection.SOUTHEAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), MapDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, -1), MapDirection.SOUTHWEST.toUnitVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), MapDirection.NORTHWEST.toUnitVector());
    }

}