package model.util;

import model.Vector2d;
import model.WorldMap;
import model.interfaces.PositionsGenerator;

import java.util.*;

public class GrassPositionsGenerator implements PositionsGenerator {

    private final List<Vector2d> positions;
    private final WorldMap map;

    public GrassPositionsGenerator(WorldMap map, int count) {
        List<Vector2d> allPositions = generateAllPositions(map.getWidth(), map.getHeight());

        for(Vector2d position: map.getPlants().keySet()){ //remove positions where grass is currently growing
            allPositions.remove(position);
        }
        this.map = map;
        this.positions = selectRandomPositions(allPositions, count);
    }

    private List<Vector2d> generateAllJunglePositions(List<Vector2d> allPositions){
        List<Vector2d> allJunglePositions = new ArrayList<>();
        for(Vector2d position: allPositions){
            if(map.getJungleBounds().isItWithinTheBoundaries(position)){
                allJunglePositions.add(position);
            }
        }
        return allJunglePositions;
    }

    @Override
    public List<Vector2d> selectRandomPositions(List<Vector2d> allPositions, int accessiblePlaces) {
        List <Vector2d> junglePositions = generateAllJunglePositions(allPositions);
        List <Vector2d> notJunglePositions = new ArrayList<>(allPositions);
        notJunglePositions.removeAll(junglePositions);
        Collections.shuffle(junglePositions, map.getRandom());
        Collections.shuffle(notJunglePositions, map.getRandom());

        List<Vector2d> selectedPositions = new ArrayList<>();
        int jungleIdx = 0;
        int notJungleIdx = 0;

        while(accessiblePlaces > 0){
            int randomNum = map.getRandom().nextInt(10); //problem of that solution is that it can be draw too many times
            if(randomNum < 8 && jungleIdx < junglePositions.size()){
                selectedPositions.add(junglePositions.get(jungleIdx));
                jungleIdx++;
                accessiblePlaces--;
            }
            else if(notJungleIdx < notJunglePositions.size()){
                selectedPositions.add(notJunglePositions.get(notJungleIdx));
                notJungleIdx++;
                accessiblePlaces--;
            }
            else if(jungleIdx >= junglePositions.size()){
                accessiblePlaces = 0;
            }
        }

        return selectedPositions;
    }


    @Override
    public Iterator<Vector2d> iterator() {
        return positions.iterator();
    }



}
