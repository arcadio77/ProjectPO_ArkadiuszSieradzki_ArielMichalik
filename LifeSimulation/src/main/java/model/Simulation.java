package model;
import model.util.Energy;

import java.util.Random;

public class Simulation implements Runnable{
    private final WorldMap map;
    private int speed;
    private boolean pauseThreadFlag = false;
    private final Object GUI_INITIALIZATION_MONITOR = new Object();

    public Simulation(WorldMap map, int speed){
        this.map = map;
        this.speed = speed;
    }

    public void run(){
        //TODO make CSV file saver
        OneDayRunner oneDay = new OneDayRunner(map);

        while(!map.getAnimals().isEmpty()){
            checkForPaused();
            oneDay.runOneDay();
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void pauseSimulation() {
        pauseThreadFlag = true;
    }

    public void continueSimulation(){
        synchronized(GUI_INITIALIZATION_MONITOR) {
            pauseThreadFlag = false;
            GUI_INITIALIZATION_MONITOR.notify();
        }
    }

    private void checkForPaused() {
        synchronized (GUI_INITIALIZATION_MONITOR) {
            while (pauseThreadFlag) {
                try {
                    GUI_INITIALIZATION_MONITOR.wait();
                } catch (Exception e) {}
            }
        }
    }

}
