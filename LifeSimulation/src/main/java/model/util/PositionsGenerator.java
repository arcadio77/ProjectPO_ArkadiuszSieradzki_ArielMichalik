package model.util;

import model.GrassField;
import model.Vector2d;
import java.util.*;

public class PositionsGenerator implements Iterable<Vector2d> {
    private final List<Vector2d> positions;
    private final Random random;

    public PositionsGenerator(int width, int height, int count) {
        this(width, height, count, new Random());
    }

    public PositionsGenerator(int width, int height, int count, Random random) {
        this.random = random;
        List<Vector2d> allPositions = generateAllPositions(width, height);
        this.positions = selectRandomPositions(allPositions, count);
    }

    private List<Vector2d> generateAllPositions(int width, int height) {
        List<Vector2d> allPositions = new ArrayList<>();
        for (int i = 0; i <= height; i++) {
            for (int j = 0; j <= width; j++) {
                allPositions.add(new Vector2d(i, j));
            }
        }
        return allPositions;
    }

    private List<Vector2d> selectRandomPositions(List<Vector2d> allPositions, int count) {
        //positions never repeat works fine for plants but not for animals -> question is do we want put animals on the same field or put them on seperate ones but let them move to the same
        List<Vector2d> selectedPositions = new ArrayList<>();
        Collections.shuffle(allPositions, random);
        for (int i = 0; i < Math.min(count, allPositions.size()); i++) {
            selectedPositions.add(allPositions.get(i));
        }
        return selectedPositions;
    }

//
//    private List<Vector2d> selectRandomPositionsAnimals(){
//        //different approach for animals maybe to do later
//    }


    @Override
    public Iterator<Vector2d> iterator() {
        return positions.iterator();
    }
}
