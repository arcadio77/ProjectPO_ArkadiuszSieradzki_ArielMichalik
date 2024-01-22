package model;

public class Simulation implements Runnable{
    private final WorldMap map;
    private final int speed;
    private final Statistics stats;
    private boolean pauseThreadFlag = false;
    private final Object GUI_INITIALIZATION_MONITOR = new Object();

    public Simulation(WorldMap map, int speed, Statistics stats){
        this.map = map;
        this.speed = speed;
        this.stats = stats;
    }

    public void run(){
        String filePath = "output" + (map.getMapID() + 1) + ".csv";
        CsvSaver csvSaver = new CsvSaver(stats);
        csvSaver.createCsvFile(filePath);
        OneDayRunner oneDay = new OneDayRunner(map);

        while(!map.getAnimals().isEmpty()){
            checkForPaused();
            oneDay.runOneDay();
            csvSaver.addRow(filePath);
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
                } catch (Exception ignored) {}
            }
        }
    }

}
