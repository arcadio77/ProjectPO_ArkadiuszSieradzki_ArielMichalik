package model;

import model.util.Energy;

import java.util.Random;

public class OneCycleTest {

    public static void main(String[] args){  // will work if ariello updates adding animals to our map
        WorldMap gF = new WorldMap(10, 10, 10, 10,  new Energy(1, 2, 3, 5), 5, 5,
                1, 1, new Random());
        OneDayRunner oneDay = new OneDayRunner(gF);
        oneDay.runOneDay();
    }

}
