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

    public GrassPositionsGenerator(int width, int height, int count, Boundary jungleBounds, Map<Vector2d, Grass> plants, Random random) {
        List<Vector2d> allPositions = generateAllPositions(width, height);
        this.plants = plants;
        this.random = random;
        this.jungleBounds = jungleBounds;
        this.positions = selectRandomPositions(allPositions, count);
    }

    //TODO after all animals die grass don't cover whole map even if there is only one empty cell in many days any grass is growing there, it's not jungle territory

    private List<Vector2d> generateAllJunglePositions(List<Vector2d> allPositions){
        List<Vector2d> allJunglePositions = new ArrayList<>();
        for(Vector2d position: allPositions){
            if(jungleBounds.isItWithinTheBoundaries(position)){
                allJunglePositions.add(position);
            }
        }
        for(Vector2d grassPosition : plants.keySet()){
            allJunglePositions.remove(grassPosition);
        }
        return allJunglePositions;
    }

    @Override
    public List<Vector2d> selectRandomPositions(List<Vector2d> allPositions, int count) {
        List <Vector2d> junglePositions = generateAllJunglePositions(allPositions);
        List <Vector2d> notJunglePositions = new ArrayList<>(allPositions);
        notJunglePositions.removeAll(junglePositions);
        Collections.shuffle(junglePositions, random);
        Collections.shuffle(notJunglePositions, random);

        List<Vector2d> selectedPositions = new ArrayList<>();
        int jungleIdx = 0;
        int notJungleIdx = 0;

        while(count > 0){
            int randomNum = random.nextInt(10); //problem of that solution is that it can be draw too many times
            if(randomNum < 8 && jungleIdx < junglePositions.size()){
                selectedPositions.add(junglePositions.get(jungleIdx));
                jungleIdx++;
                count--;
            }
            else if(notJungleIdx < notJunglePositions.size()){
                selectedPositions.add(notJunglePositions.get(notJungleIdx));
                notJungleIdx++;
                count--;
            }
            else if(jungleIdx >= junglePositions.size()){
                //counter is bigger the accessible places for grass on the map
                count = 0;
            }
        }

        return selectedPositions;
    }


    @Override
    public Iterator<Vector2d> iterator() {
        return positions.iterator();
    }



}
