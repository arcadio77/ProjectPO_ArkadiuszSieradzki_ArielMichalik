package model.util;

import model.Vector2d;
import model.WorldMap;
import model.interfaces.PositionsGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AnimalPositionsGenerator implements PositionsGenerator {
    private final List<Vector2d> positions;
    private final Random random;

    public AnimalPositionsGenerator(WorldMap map, int count) {
        List<Vector2d> allPositions = generateAllPositions(map.getWidth(), map.getHeight());
        this.random = map.getRandom();
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
    public @NotNull Iterator<Vector2d> iterator() {
        return positions.iterator();
    }
}
