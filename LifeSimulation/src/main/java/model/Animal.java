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
    private ArrayList<Animal> children = new ArrayList<>();
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


    public ArrayList<Integer> getGenome() {
        return this.genome.getGenome();
    }

    @Override
    public Vector2d getPosition(){
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


    public void move(Vector2d lowerLeft, Vector2d upperRight){

        this.energy--; // daleko jeszcze???
        this.age++; // starość nie radość

        this.geneId = (this.geneId + 1) % this.genome.getGenome().size();
        int gene = this.genome.getGenome().get(geneId);

        int curr_orientation_int = this.direction.fromEnum();
        int new_orientation_int = (curr_orientation_int + gene) % 8;

        MapDirection new_orientation = MapDirection.fromInteger(new_orientation_int);
        Vector2d new_position = this.position.add(new_orientation.toUnitVector());

        Vector2d upperLeft = new Vector2d(lowerLeft.getX(), upperRight.getY());
        Vector2d lowerRight = new Vector2d(upperRight.getX(), lowerLeft.getY());

        // not borders
        if (position.getX() > lowerLeft.getX() && position.getX() < upperRight.getX() &&
            position.getY() > lowerLeft.getY() && position.getY() < upperRight.getY()){
            this.position = new_position;
            this.direction = new_orientation;
        }

        // borders but not corners
        else if (!(position.equals(lowerLeft) || position.equals(upperLeft) || position.equals(upperRight) || position.equals(lowerRight))) {
            //right -> move animal to the left part of the map

            if(new_position.getX() > upperRight.getX()){
                this.position = new Vector2d(lowerLeft.getX(), new_position.getY());
            }

            // loop left
            if (new_position.getX() < lowerLeft.getX()){
                this.position = new Vector2d(upperRight.getX(), new_position.getY());
            }

            //turn around South Pole
            if(new_position.getY() < lowerLeft.getY()){
                this.direction = direction.opposite();
            }

            // turn around North Pole
            if (new_position.getY() > upperRight.getY()){
                this.direction = direction.opposite();
            }
        }

        // corners
        else{
            //3 subcases, nextPosition can be to the: 1) right/left -> looping around, 2) up/down -> turn around Pole, 3) diagonal -> act as Pole (turn animal around)
            if(position.equals(lowerLeft)){
                if(new_position.getX() < lowerLeft.getX() && new_position.getY() >= lowerLeft.getY()){
                    this.position = new Vector2d(lowerRight.getX(), new_position.getY());
                }
                if(new_position.getY() < lowerLeft.getY() && new_position.getX() >= lowerLeft.getX()){
                    this.direction = direction.opposite();
                }
                if(new_position.getX() < lowerLeft.getX() && new_position.getY() < lowerLeft.getY()){
                    this.direction = direction.opposite();
                }
            }
            if(position.equals(lowerRight)){
                if(new_position.getX() > lowerRight.getX() && new_position.getY() >= lowerRight.getY()){
                    this.position = new Vector2d(lowerLeft.getX(), new_position.getY());
                }
                if(new_position.getY() < lowerRight.getY() && new_position.getX() <= lowerRight.getX()){
                    this.direction = direction.opposite();
                }
                if(new_position.getX() > lowerRight.getX() && new_position.getY() < lowerRight.getY()){
                    this.direction = direction.opposite();
                }
            }
            if(position.equals(upperLeft)){
                if(new_position.getX() < upperLeft.getX() && new_position.getY() <= upperLeft.getY()){
                    this.position = new Vector2d(upperRight.getX(), new_position.getY());
                }
                if(new_position.getY() > upperLeft.getY() && new_position.getX() >= upperLeft.getX()){
                    this.direction = direction.opposite();
                }
                if(new_position.getX() < upperLeft.getX() && new_position.getY() > upperLeft.getY()){
                    this.direction = direction.opposite();
                }
            }
            if(position.equals(upperRight)){
                if(new_position.getX() > upperRight.getX() && new_position.getY() <= upperRight.getY()){
                    this.position = new Vector2d(upperLeft.getX(), new_position.getY());
                }
                if(new_position.getY() > upperRight.getY() && new_position.getX() <= upperRight.getX()){
                    this.direction = direction.opposite();
                }
                if(new_position.getX() > upperRight.getX() && new_position.getY() > upperRight.getY()){
                    this.direction = direction.opposite();
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

    // <---------------------------------------------------------------------------------------------->
    //                                              OTHER
    // <---------------------------------------------------------------------------------------------->

    /*
    @Override
    public String toString() {
        return switch (this.orientation){
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case SOUTH -> "S";
            case SOUTHEAST -> "SE";
            case EAST -> "E";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "NW";
        };
    }*/

    /*
    @Override
    public String toString() {
        return String.valueOf(id);
    }*/


    public String toString() {
        return String.valueOf(energy);
    }

    public int getId() {
        return id;
    }
}
