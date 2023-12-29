package model;

import model.enums.MapDirection;
import model.interfaces.WorldElement;
import java.util.ArrayList;

public class Animal implements WorldElement {
    private MapDirection orientation;
    private Vector2d position;
    private int energy;
    private final Genome genome;
    private final int genomeLength;
    private Integer geneId;
    private int age;
    private ArrayList<Animal> children = new ArrayList<>();

    public Animal(Vector2d x){
        this(x, MapDirection.NORTH, new Genome(10, new Mutation(0,0)), 0);
    }

    public Animal(Vector2d x, MapDirection dir, Genome genome, Integer geneId){
        this.position = x;
        this.orientation = dir;
        this.genome = genome;
        this.geneId = geneId;
        this.genomeLength = genome.getGenome().size();
    }

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
    }
    @Override
    public boolean isAt(Vector2d pos){
        return this.position.equals(pos);
    }

    public void move(){

        this.energy--; // daleko jeszcze???
        this.age++;

        this.geneId = (this.geneId + 1) % this.genomeLength;
        int gene = this.genome.getGenome().get(geneId);

        int curr_orientation_int = this.orientation.fromEnum();
        int new_orientation_int = curr_orientation_int + gene;

        MapDirection new_orientation = MapDirection.fromInteger(new_orientation_int);
        Vector2d new_position = this.position.add(new_orientation.toUnitVector());

        //jeżeli w mapie
        this.position = new_position;
        this.orientation = new_orientation;

        //warunki brzegowe

    }

    public void breed(Animal child){
        children.add(child);
    }

    public void eat(int grassEnergy){
        this.energy += grassEnergy;
    }



    //odwołuje się do funkcje w klasie genome ktora zwraca genome (ArrayList)
    public ArrayList<Integer> getGenome() {
        return this.genome.getGenome();
    }

    @Override
    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getOrientation(){
        return this.orientation;
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
}
