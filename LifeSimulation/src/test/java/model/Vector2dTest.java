package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    void testToString() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(-1, 0);
        assertEquals("(3,5)", vector1.toString());
        assertEquals("(-1,0)", vector2.toString());
    }

    @Test
    void testAdd() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(-2, 7);
        Vector2d vector3 = new Vector2d(0, 0);
        assertEquals(new Vector2d(1, 12), vector1.add(vector2));
        assertEquals(vector1, vector1.add(vector3));
        assertEquals(vector2, vector2.add(vector3));
    }

    @Test
    void testIsBiggerOrEqual() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(1, 3);
        Vector2d vector3 = new Vector2d(3, 5);
        assertTrue(vector1.isBiggerOrEqual(vector2));
        assertFalse(vector2.isBiggerOrEqual(vector1));
        assertTrue(vector1.isBiggerOrEqual(vector3));
    }

    @Test
    void testIsBigger() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(1, 3);
        Vector2d vector3 = new Vector2d(3, 5);
        assertTrue(vector1.isBigger(vector2));
        assertFalse(vector2.isBigger(vector1));
        assertFalse(vector1.isBigger(vector3));
    }

    @Test
    void testIsSmallerOrEqual() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(5, 8);
        Vector2d vector3 = new Vector2d(3, 5);
        assertTrue(vector1.isSmallerOrEqual(vector2));
        assertFalse(vector2.isSmallerOrEqual(vector1));
        assertTrue(vector1.isSmallerOrEqual(vector3));
    }

    @Test
    void testIsSmaller() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(5, 8);
        Vector2d vector3 = new Vector2d(3, 5);
        assertTrue(vector1.isSmaller(vector2));
        assertFalse(vector2.isSmaller(vector1));
        assertFalse(vector1.isSmaller(vector3));
    }

    @Test
    void testEquals() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(3, 5);
        Vector2d vector3 = new Vector2d(1, 3);

        assertEquals(vector1, vector2);
        assertNotEquals(vector1, vector3);
        assertNotEquals(vector2, vector3);
    }

    @Test
    void testEqualsReflexive() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(-1, 0);
        assertEquals(vector1, vector1);
        assertEquals(vector2, vector2);
    }

    @Test
    void testEqualsSymmetric() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(3, 5);
        Vector2d vector3 = new Vector2d(-1, 0);

        assertEquals(vector1, vector2);
        assertEquals(vector2, vector1);
        assertNotEquals(vector1, vector3);
        assertNotEquals(vector3, vector1);
    }

    @Test
    void testEqualsTransitive() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(3, 5);
        Vector2d vector3 = new Vector2d(3, 5);

        assertEquals(vector1, vector2);
        assertEquals(vector2, vector3);
        assertEquals(vector1, vector3);
    }

    @Test
    void testEqualsConsistent() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(3, 5);

        assertEquals(vector1, vector2);
        assertEquals(vector1, vector2);
        assertEquals(vector1, vector2);
    }

    @Test
    void testEqualsWithNull() {
        Vector2d vector = new Vector2d(3, 5);
        assertNotEquals(null, vector);
    }

    @Test
    void testEqualsWithDifferentClass() {
        Vector2d vector = new Vector2d(3, 5);
        assertNotEquals("This is a string", vector);
    }

    @Test
    void testHashCodeConsistentWithEquals() {
        Vector2d vector1 = new Vector2d(3, 5);
        Vector2d vector2 = new Vector2d(3, 5);

        assertEquals(vector1, vector2);
        assertEquals(vector1.hashCode(), vector2.hashCode());
    }

}
