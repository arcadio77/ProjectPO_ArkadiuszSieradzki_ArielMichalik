package model.util;

import model.Vector2d;
import model.interfaces.PositionsGenerator;

import java.util.*;

public class AnimalPositionsGenerator implements PositionsGenerator {
    private final List<Vector2d> positions;
    private final Random random;

    public AnimalPositionsGenerator(int width, int height, int count, Random random) {
        List<Vector2d> allPositions = generateAllPositions(width, height);
        this.random = random;
        this.positions = selectRandomPositions(allPositions, count);
    }

    @Override
    public List<Vector2d> selectRandomPositions(List<Vector2d> allPositions, int count) {
        List<Vector2d> selectedPositions = new ArrayList<>();
        Collections.shuffle(allPositions, random);
        while(count > 0){
            int i = random.nextInt(allPositions.size());
            selectedPositions.add(allPositions.get(i));
            count--;
        }
        return selectedPositions;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return positions.iterator();
    }
}
