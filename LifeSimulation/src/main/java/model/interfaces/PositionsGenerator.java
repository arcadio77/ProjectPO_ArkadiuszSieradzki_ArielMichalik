package model.interfaces;


import model.Vector2d;
import java.util.*;

public interface PositionsGenerator extends Iterable<Vector2d> { ;
    public default List<Vector2d> generateAllPositions(int width, int height) {
        List<Vector2d> allPositions = new ArrayList<>();
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) {
                allPositions.add(new Vector2d(i, j));
            }
        }
        return allPositions;
    }
    List<Vector2d> selectRandomPositions(List<Vector2d> allPositions, int count);


}
