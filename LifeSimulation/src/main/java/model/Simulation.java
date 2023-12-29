package model;

public class Simulation extends SimulationGivenData{
    private GrassField map;
    private SimulationGivenData data;

    public Simulation(){
        super(10, 10, 10, 10, 5, 5, 1,
                2, 1, 10, 1, 2);
    }

    public void run(){
        GrassField map = new GrassField(width, height, initialAnimalsNumber, );
        OneCycle oneDay = new OneCycle(map);
        oneDay.runOneCycle();
    }

}
