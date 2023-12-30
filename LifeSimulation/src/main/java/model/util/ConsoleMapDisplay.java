package model.util;
import model.interfaces.MapChangeListener;
import model.GrassField;

public class ConsoleMapDisplay implements MapChangeListener {
    private int updatesCnt =0;
    @Override
    public synchronized void mapChanged(GrassField worldMap, String message) {
        System.out.println(message);
        System.out.print(worldMap);
        updatesCnt++;
        System.out.println("Number of changes is equal to:  " + updatesCnt + "\n\n");
    }
}
