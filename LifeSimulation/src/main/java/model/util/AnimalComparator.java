package model.util;

import model.Animal;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal a1, Animal a2){
        if(a1.getEnergy() == a2.getEnergy()){
            if(a1.getAge()==a2.getAge()){
                if(a1.getChildren().size()==a2.getChildren().size()){
                    Random rand = new Random();
                    double randomNum = rand.nextDouble();
                    return 0;
                }
                return a1.getChildren().size()>a2.getChildren().size() ? -1 : 1;
            }
            return a1.getAge()>a2.getAge() ? -1: 1;
        }
        return a1.getEnergy()>a2.getEnergy() ? -1 : 1;
    }
}