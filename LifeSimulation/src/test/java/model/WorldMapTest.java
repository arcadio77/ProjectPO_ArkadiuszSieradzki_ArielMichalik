package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorldMapTest {
    @Test
    public void TestSetJungleBounds(){
        WorldMap map1 = new WorldMap(4, 5);
        map1.setJungleBounds();
        System.out.println(map1.setJungleBounds());
        assertEquals(new Boundary( new Vector2d(0, 2), new Vector2d(4, 3)), map1.setJungleBounds());

        WorldMap map2 = new WorldMap(4, 4);
        map2.setJungleBounds();
        System.out.println(map2.setJungleBounds());
        assertEquals(new Boundary( new Vector2d(0, 2), new Vector2d(4, 3)), map2.setJungleBounds());

        WorldMap map3 = new WorldMap(5, 4);
        map3.setJungleBounds();
        System.out.println(map3.setJungleBounds());
        assertEquals(new Boundary( new Vector2d(0, 2), new Vector2d(5, 3)), map3.setJungleBounds());

        WorldMap map4 = new WorldMap(10, 10);
        map4.setJungleBounds();
        System.out.println(map4.setJungleBounds());
        assertEquals(new Boundary( new Vector2d(0, 4), new Vector2d(10, 6)), map3.setJungleBounds());

    }

}
