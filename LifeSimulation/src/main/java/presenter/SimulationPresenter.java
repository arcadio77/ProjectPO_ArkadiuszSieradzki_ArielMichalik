package presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import model.*;

import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.Energy;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SimulationPresenter implements MapChangeListener {
    public ComboBox<String> configurations;
    private WorldMap map;
    public Button startBtn;
    public TextField getWidth;
    public TextField getHeight;
    public TextField getAnimalNumber;
    public TextField getInitialGrassNumber;
    public TextField getInitialAnimalEnergy;
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
        System.out.println("niceee");
        Platform.runLater(this::drawMap);
    }


    public int getWidthValue() {
        return Integer.parseInt(getWidth.getText());
    }

    public int getHeightValue() {
        return Integer.parseInt(getHeight.getText());
    }

    public int getAnimalNumberValue() {
        return Integer.parseInt(getAnimalNumber.getText());
    }

    public int getInitialGrassNumberValue() {
        return Integer.parseInt(getInitialGrassNumber.getText());
    }
    public int getInitialAnimalEnergyValue(){
        return Integer.parseInt(getInitialAnimalEnergy.getText());
    }
    public int getNumOfGrassGrowingDailyValue() {
        return Integer.parseInt(getNumOfGrassGrowingDaily.getText());
    }

    public int getGrassEnergyValue() {
        return Integer.parseInt(getGrassEnergy.getText());
    }

    public int getBreedReadyEnergyValue() {
        return Integer.parseInt(getBreedReadyEnergy.getText());
    }

    public int getBreedLostEnergyValue() {
        return Integer.parseInt(getBreedLostEnergy.getText());
    }

    public int getGenomeLengthValue() {
        return Integer.parseInt(getGenomeLength.getText());
    }

    public int getMinMutationNumValue() {
        return Integer.parseInt(getMinMutationNum.getText());
    }

    public int getMaxMutationNumValue() {
        return Integer.parseInt(getMaxMutationNum.getText());
    }

    public boolean getUseMutationSwapGeneValue() {
        return getUseMutationSwapGene.isSelected();
    }

    public boolean getUseLifeGivingCorpsesValue() {
        return getUseLifeGivingCorpses.isSelected();
    }


    
    // Simulation simulation = new SimulationGivenData(getWidthValue(), getHeightValue(), getAnimalNumberValue(), getInitialGrassNumberValue(),
    //         getInitialAnimalEnergyValue(),getNumOfGrassGrowingDailyValue(), getGrassEnergyValue(), getBreedReadyEnergyValue(), getBreedLostEnergyValue(),
    //         getGenomeLengthValue(), getMinMutationNumValue(), getMaxMutationNumValue(), getUseMutationSwapGeneValue(), getUseLifeGivingCorpsesValue());

    // simulation.run();

    public void onSimulationStartClicked() throws IllegalArgumentException{
        System.out.println("clicked");


        settingMap();

        Simulation simulation = new Simulation(this.map);
        //simulation.run();
        //createGrid(10,10);

        SimulationEngine engine = new SimulationEngine(new ArrayList<>(List.of(simulation)), 4);
        System.out.println("started");

        engine.runAsync();


    }

    private void settingMap(){
        this.map.setWidth(getWidthValue());
        this.map.setHeight(getHeightValue());
        this.map.setAnimalsNumber(getAnimalNumberValue());
        this.map.setPlantsNumber(getInitialGrassNumberValue());
        Energy energy = new Energy(getGrassEnergyValue(), getBreedReadyEnergyValue(), getBreedLostEnergyValue(), getInitialAnimalEnergyValue());
        this.map.setEnergy(energy);
        this.map.setMutation(new Mutation(getMinMutationNumValue(), getMaxMutationNumValue()));
        this.map.setGenomeLength(getGenomeLengthValue());
        this.map.setNumOfGrassGrowingDaily(getNumOfGrassGrowingDailyValue());
        this.map.setRandom(new Random());
        this.map.setUseMutationSwapGene(getUseMutationSwapGeneValue());
        this.map.setUseLifeGivingCorpses(getUseLifeGivingCorpsesValue());
    }

    public void onConfigurationSelected(ActionEvent event) {
        String selectedConfiguration = configurations.getValue();
        if (selectedConfiguration.equals("Configuration 1")) {
            getWidth.setText("10");
            getHeight.setText("10");
            getAnimalNumber.setText("10");
            getInitialAnimalEnergy.setText("10");
            getInitialGrassNumber.setText("10");
            getNumOfGrassGrowingDaily.setText("10");
            getGrassEnergy.setText("10");
            getBreedReadyEnergy.setText("10");
            getBreedLostEnergy.setText("10");
            getGenomeLength.setText("10");
            getMinMutationNum.setText("10");
            getMaxMutationNum.setText("10");
        } else if (selectedConfiguration.equals("Configuration 2")) {
            getWidth.setText("20");
            getHeight.setText("20");
            getAnimalNumber.setText("20");
            getInitialAnimalEnergy.setText("20");
            getInitialGrassNumber.setText("20");
            getNumOfGrassGrowingDaily.setText("20");
            getGrassEnergy.setText("20");
            getBreedReadyEnergy.setText("20");
            getBreedLostEnergy.setText("20");
            getGenomeLength.setText("20");
            getMinMutationNum.setText("20");
            getMaxMutationNum.setText("20");
            getUseMutationSwapGene.setSelected(true);
            getUseLifeGivingCorpses.setSelected(true);
        }
    }

}
