package model;

import model.enums.MapDirection;
import model.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements WorldElement {
    //TODO clean
    private final int id;
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final Genome genome;
    private Integer geneId;
    private int age;
    private final ArrayList<Animal> children = new ArrayList<>();
    private static int idCnt = 0;

    public Animal(Vector2d x){
        this(x, MapDirection.NORTH, new Genome(10, new Mutation(0,0), new Random()), 0, 5);
    }

    public Animal(Vector2d x, MapDirection dir, Genome genome, Integer geneId, int initEnergy){
        this.id = idCnt;
        idCnt ++;
        this.position = x;
        this.direction = dir;
        this.genome = genome;
        this.geneId = geneId;
        this.energy = initEnergy;
    }


    public ArrayList<Integer> getGenomeList() {
        return this.genome.getGenome();
    }

    @Override
    public Vector2d position(){
        return this.position;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<Animal> getChildren() {
        return children;
    }

    public MapDirection getNewOrientation(){
        int gene = genome.getGenome().get(geneId);
        int currOrientationInt = this.direction.fromEnum();
        int newOrientationInt = (currOrientationInt + gene) % 8;
        return MapDirection.fromInteger(newOrientationInt);
    }

    public Vector2d getNewPosition(MapDirection newOrientation){
        return position.add(newOrientation.toUnitVector());
    }
    public void readNextGene(){
        geneId = (geneId + 1) % genome.getGenome().size();
    }


    public boolean isInTheMiddle(Vector2d lowerLeft, Vector2d upperRight){
        return position.isBigger(lowerLeft) && position.isSmaller(upperRight);
    }



    public void move(Vector2d lowerLeft, Vector2d upperRight){ //this method change position or direction or both of the animal on the map

        this.energy--; // daleko jeszcze???
        this.age++; // starość nie radość
        readNextGene();

        MapDirection newOrientation = getNewOrientation();
        Vector2d newPosition = getNewPosition(newOrientation);

        Vector2d upperLeft = new Vector2d(lowerLeft.x(), upperRight.y());
        Vector2d lowerRight = new Vector2d(upperRight.x(), lowerLeft.y());

        if (isInTheMiddle(lowerLeft, upperRight)){
            this.position = newPosition;
            this.direction = newOrientation;
        }

        // borders but not corners
        else if (!(position.equals(lowerLeft) || position.equals(upperLeft) || position.equals(upperRight) || position.equals(lowerRight))) {
            //right -> move animal to the left part of the map

            if(newPosition.x() > upperRight.x()){
                this.position = new Vector2d(lowerLeft.x(), newPosition.y());
            }

            // loop left
            if (newPosition.x() < lowerLeft.x()){
                this.position = new Vector2d(upperRight.x(), newPosition.y());
            }

            //turn around South Pole
            if(newPosition.y() < lowerLeft.y()){
                this.direction = direction.opposite();
            }

            // turn around North Pole
            if (newPosition.y() > upperRight.y()){
                this.direction = direction.opposite();
            }
        }

        // corners
        else{
            //3 subcases, nextPosition can be to the: 1) right/left -> looping around, 2) up/down -> turn around Pole, 3) diagonal -> act as Pole (turn animal around)
            if(position.equals(lowerLeft)){
                if(newPosition.x() < lowerLeft.x() && newPosition.y() >= lowerLeft.y()){
                    this.position = new Vector2d(lowerRight.x(), newPosition.y());
                }
                if(newPosition.y() < lowerLeft.y() && newPosition.x() >= lowerLeft.x()){
                    this.direction = direction.opposite();
                }
                if(newPosition.isSmaller(lowerLeft)){
                    this.direction = direction.opposite();
                    this.position = new Vector2d(lowerRight.x(), newPosition.y());

                }
            }
            if(position.equals(lowerRight)){
                if(newPosition.x() > lowerRight.x() && newPosition.y() >= lowerRight.y()){
                    this.position = new Vector2d(lowerLeft.x(), newPosition.y());
                }
                if(newPosition.y() < lowerRight.y() && newPosition.x() <= lowerRight.x()){
                    this.direction = direction.opposite();
                }
                if(newPosition.x() > lowerRight.x() && newPosition.y() < lowerRight.y()){
                    this.direction = direction.opposite();
                    this.position = new Vector2d(lowerLeft.x(), newPosition.y());

                }
            }
            if(position.equals(upperLeft)){
                if(newPosition.x() < upperLeft.x() && newPosition.y() <= upperLeft.y()){
                    this.position = new Vector2d(upperRight.x(), newPosition.y());
                }
                if(newPosition.y() > upperLeft.y() && newPosition.x() >= upperLeft.x()){
                    this.direction = direction.opposite();
                }
                if(newPosition.x() < upperLeft.x() && newPosition.y() > upperLeft.y()){
                    this.direction = direction.opposite();
                    this.position = new Vector2d(upperRight.x(), newPosition.y());

                }
            }
            if(position.equals(upperRight)){
                if(newPosition.x() > upperRight.x() && newPosition.y() <= upperRight.y()){
                    this.position = new Vector2d(upperLeft.x(), newPosition.y());
                }
                if(newPosition.y() > upperRight.y() && newPosition.x() <= upperRight.x()){
                    this.direction = direction.opposite();
                }
                if(newPosition.isBigger(upperRight)){
                    this.direction = direction.opposite();
                    this.position = new Vector2d(upperLeft.x(), newPosition.y());

                }
            }
        }
    }


    public void breed(Animal child, int lostEnergy){
        System.out.println("breeding");
        this.energy -= lostEnergy;
        children.add(child);
    }

    public void eat(int grassEnergy){
        this.energy += grassEnergy;
    }

    public String toString() {
        return String.valueOf(energy);
    }

    public int getId() {
        return id;
    }
}
