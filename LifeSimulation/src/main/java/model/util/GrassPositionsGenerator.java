package model.util;

import model.Boundary;
import model.Grass;
import model.Vector2d;
import model.interfaces.PositionsGenerator;

import java.util.*;

public class GrassPositionsGenerator implements PositionsGenerator {

    private final List<Vector2d> positions;
    private final Random random;
    private final Map<Vector2d, Grass> plants;
    private final Boundary jungleBounds;

    //TODO maybe we need to give map reference as a parameter then we have getters for jungleBounds, plants and random
    public GrassPositionsGenerator(int width, int height, int count, Boundary jungleBounds, Map<Vector2d, Grass> plants, Random random) {
        List<Vector2d> allPositions = generateAllPositions(width, height);

        for(Vector2d position: plants.keySet()){ //remove positions where grass is currently growing
            allPositions.remove(position);
        }

        this.plants = plants;
        this.random = random;
        this.jungleBounds = jungleBounds;
        this.positions = selectRandomPositions(allPositions, count);
    }

    private List<Vector2d> generateAllJunglePositions(List<Vector2d> allPositions){
        List<Vector2d> allJunglePositions = new ArrayList<>();
        for(Vector2d position: allPositions){
            if(jungleBounds.isItWithinTheBoundaries(position)){
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
        Collections.shuffle(junglePositions, random);
        Collections.shuffle(notJunglePositions, random);

        List<Vector2d> selectedPositions = new ArrayList<>();
        int jungleIdx = 0;
        int notJungleIdx = 0;

        while(accessiblePlaces > 0){
            int randomNum = random.nextInt(10); //problem of that solution is that it can be draw too many times
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
