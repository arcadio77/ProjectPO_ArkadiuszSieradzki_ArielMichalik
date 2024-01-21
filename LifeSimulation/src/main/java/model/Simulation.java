package model;
import model.util.Energy;

import java.util.Random;

public class Simulation implements Runnable{

    private final WorldMap map;
    boolean isRunning;

    public Simulation(WorldMap map){
        this.map = map;
        isRunning = true;
    }

    public void run(){
        //TODO make CSV file saver
        OneDayRunner oneDay = new OneDayRunner(map);

        for(int i=0; i < 200; i++){
            oneDay.runOneDay();
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopSimulation() {
        //TODO stop simulation
        isRunning = false;
    }

    public void continueSimulation(){
        //TODO continue simulation
        isRunning = true;
    }
}
