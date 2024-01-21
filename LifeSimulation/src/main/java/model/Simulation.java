package model;
import model.util.Energy;

import java.util.Random;

public class Simulation implements Runnable{

    private final WorldMap map;
    boolean isRunning;

    private int speed;

    public Simulation(WorldMap map, int speed){
        this.map = map;
        isRunning = true;
        this.speed = speed;
    }

    public void run(){
        //TODO make CSV file saver
        OneDayRunner oneDay = new OneDayRunner(map);

        while(true){
            oneDay.runOneDay();
            try {
                Thread.sleep(speed);
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
