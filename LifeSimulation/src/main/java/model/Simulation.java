package model;
import model.util.Energy;

import java.util.Random;

public class Simulation implements Runnable{

    private final WorldMap map;

    public Simulation(WorldMap map){
        //super(10, 10, 40, 50, 10, 5, 1,
        //       2, 1, 10, 1, 2, 123456789L,
        //        true, true);
        this.map = map;
    }

    public void run(){

        //TODO still different variations of the map are printing - don't know why?
        OneDayRunner oneDay = new OneDayRunner(map);
        //System.out.println(map); // -> console

        for(int i=0; i < 200; i++){
            oneDay.runOneDay();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //System.out.println(map); // -> console
        }
    }
}
