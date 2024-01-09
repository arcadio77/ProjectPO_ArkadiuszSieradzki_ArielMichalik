package model.util;

import model.WorldMap;
import model.interfaces.MapChangeListener;

public class ConsoleMapDisplay implements MapChangeListener {
    private int updatesCnt = 0;
    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message);
        System.out.print(worldMap);
        updatesCnt++;
        System.out.println("Number of changes is equal to:  " + updatesCnt + "\n\n");
    }
}