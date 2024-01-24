package model;

import model.enums.MapDirection;
import model.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements WorldElement, Cloneable {
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private final Genome genome;
    private Integer geneId;
    private int eatenPlants;
    private int deathDate;
    private int age;
    private ArrayList<Animal> children = new ArrayList<>();


    @Override
    public Animal clone() {
        try {
            return (Animal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public Animal(Vector2d x){
        this(x, MapDirection.NORTH, new Genome(10, new Mutation(0,0),  new Random()), 0, 5);
    }

    public Animal(Vector2d x, MapDirection dir, Genome genome, Integer geneId, int initEnergy){
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

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<Animal> getChildren() {
        return children;
    }

    public int getEatenPlants() {
        return eatenPlants;
    }

    public int getDeathDate() {
        return deathDate;
    }

    public Integer getGeneId() {return geneId;}

    public void setDeathDate(int deathDate) {this.deathDate = deathDate;}


    public void readNextGene(){
        geneId = (geneId + 1) % genome.getGenome().size();
    }
    private void calculateNewPosition(int width, int height){
        Vector2d newPosition = position.add(direction.toUnitVector());

        if (newPosition.x() > width){
            newPosition = newPosition.add(new Vector2d(-width - 1, 0));
        } else if (newPosition.x() < 0){
            newPosition = newPosition.add(new Vector2d(width + 1, 0));
        }
        if (newPosition.y() > height || newPosition.y() < 0){
            newPosition = new Vector2d(newPosition.x(), position.y());
            direction = direction.opposite();
        }
        this.position = newPosition;
    }

    private void getNewOrientation(){
        int gene = genome.getGenome().get(geneId);
        int currOrientationInt = this.direction.fromEnum();
        int newOrientationInt = (currOrientationInt + gene) % 8;
        this.direction = MapDirection.fromInteger(newOrientationInt);
    }

    public void move(int width, int height){
        this.energy--;
        this.age++;
        readNextGene();
        getNewOrientation();
        calculateNewPosition(width, height);
    }

    public void breed(Animal child, int lostEnergy){
        this.energy -= lostEnergy;
        if(this.energy < 0) this.energy = 0;
        this.children.add(child);
    }

    public void eat(int grassEnergy){
        this.eatenPlants ++;
        this.energy += grassEnergy;
    }

    public String toString() {
        return String.valueOf(energy);
    }

}
