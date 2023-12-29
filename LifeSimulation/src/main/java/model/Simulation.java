package model;

public class Simulation {
    private GrassField map;
    private SimulationGivenData data;

    public Simulation(){

    }

    public void run(){
        GrassField map = new GrassField();
        OneCycle oneDay = new OneCycle(map);
        oneDay.runOneCycle();
    }

}
