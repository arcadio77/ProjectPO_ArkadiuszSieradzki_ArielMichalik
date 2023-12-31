package model;

import model.util.Energy;

import java.util.Random;

public class OneCycleTest {

    public static void main(String[] args){  // will work if ariello updates adding animals to our map
        GrassField gF = new GrassField(10, 10, 10, new Energy(1, 2, 3, 5), 5, 5, 10,
                1, 0, 1);
        OneCycle oneDay = new OneCycle(gF);
        oneDay.runOneCycle();
    }

}
