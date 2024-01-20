package presenter;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.Simulation;
import model.Vector2d;
import model.WorldMap;
import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.List;


public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    public Button startBtn;
    public TextField getWidth;
    public TextField getHeight;
    public TextField getAnimalNumber;
    public TextField getInitialGrassNumber;
    public TextField getNumOfGrassGrowingDaily;
    public TextField getGrassEnergy;
    public TextField getBreedReadyEnergy;
    public TextField getBreedLostEnergy;
    public TextField getGenomeLength;
    public TextField getMinMutationNum;
    public TextField getMaxMutationNum;
    public CheckBox getUseMutationSwapGene;
    public CheckBox getUseLifeGivingCorpses;

    public GridPane gridMap;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    private void drawMap(){
        clearGrid();
        createGrid(map.getWidth(), map.getHeight());
        putElements(map.getHeight());
    }

    private void putElements(int height) {
        for(WorldElement element: map.getElements()){
            int newX = element.position().x() + 1;
            int newY = height - ( element.position().y()); //because I input values inot column from biggest to smallest
            Label elem = new Label(element.toString());
            gridMap.add(elem, newX, newY);
            GridPane.setHalignment(elem, HPos.CENTER);
        }
    }

    private void clearGrid() {
        gridMap.getChildren().retainAll(gridMap.getChildren().get(0)); // hack to retain visible grid lines
        gridMap.getColumnConstraints().clear();
        gridMap.getRowConstraints().clear();
    }

    private void createGrid(int width, int height) {
        Label separator = new Label("y\\x");
        gridMap.add(separator, 0, 0);
        GridPane.setHalignment(separator, HPos.CENTER);
        gridMap.getColumnConstraints().add(new ColumnConstraints(30));
        gridMap.getRowConstraints().add(new RowConstraints(30));

        for(int i=0; i < width; i++){
            gridMap.getColumnConstraints().add(new ColumnConstraints(30));
            Label digit = new Label(String.valueOf(i));
            gridMap.add(digit, i+1, 0); //row
            GridPane.setHalignment(digit, HPos.CENTER);
        }
        for(int i=0; i < height; i++){
            gridMap.getRowConstraints().add(new RowConstraints(30));
            Label digit = new Label(String.valueOf(height-i-1));
            gridMap.add(digit, 0, i+1);
            GridPane.setHalignment(digit, HPos.CENTER);
        }


    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }

    public void onSimulationStartClicked() throws IllegalArgumentException{
        Simulation simulation = new Simulation();
        simulation.run();


    }

}
