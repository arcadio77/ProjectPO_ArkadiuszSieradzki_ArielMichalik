package model;

import model.util.CsvSaver;

public class Simulation implements Runnable{
    private boolean running = true;
    private final WorldMap map;
    private final int speed;
    private final String filename;
    private boolean pauseThreadFlag = false;
    private final CsvSaver csvSaver;
    private final Object GUI_INITIALIZATION_MONITOR = new Object();

    public Simulation(WorldMap map, int speed, Statistics stats, String filename){
        this.map = map;
        this.speed = speed;
        this.filename = filename;
        this.csvSaver = new CsvSaver(stats);
    }

    public void run(){
        String filePath = null;
        if (filename != null){
            filePath = "SavedStats/" + filename + ".csv";
            csvSaver.createCsvFile(filePath);
        }
        OneDayRunner oneDay = new OneDayRunner(map);

        while(!map.getAnimals().isEmpty()){
            checkForPaused();
            oneDay.runOneDay();
            if(filename != null){
                csvSaver.addRow(filePath);
            }
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        running = false;
        map.mapChanged("terminated");
        if(filename != null){
            csvSaver.addRow(filePath);
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
                } catch (Exception ignored) {}
            }
        }
    }

    public boolean checkIfRunning(){
        return running;
    }

}
