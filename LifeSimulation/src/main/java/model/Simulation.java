package model;

public class Simulation implements Runnable{
    private final WorldMap map;
    private final int speed;
    private final Statistics stats;
    private final String filename;
    private boolean pauseThreadFlag = false;
    private final CsvSaver csvSaver;
    private final Object GUI_INITIALIZATION_MONITOR = new Object();

    public Simulation(WorldMap map, int speed, Statistics stats, String filename){
        this.map = map;
        this.speed = speed;
        this.stats = stats;
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

}
