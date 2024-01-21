package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Mutation;

import model.Simulation;
import model.SimulationEngine;
import model.WorldMap;
import model.interfaces.MapChangeListener;
import model.util.Energy;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarterPresenter {
    public ComboBox<String> configurations;
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


    private void settingMap( WorldMap map){
        map.setWidth(getWidthValue());
        map.setHeight(getHeightValue());
        map.setAnimalsNumber(getAnimalNumberValue());
        map.setPlantsNumber(getInitialGrassNumberValue());
        Energy energy = new Energy(getGrassEnergyValue(), getBreedReadyEnergyValue(), getBreedLostEnergyValue(), getInitialAnimalEnergyValue());
        map.setEnergy(energy);
        map.setMutation(new Mutation(getMinMutationNumValue(), getMaxMutationNumValue()));
        map.setGenomeLength(getGenomeLengthValue());
        map.setNumOfGrassGrowingDaily(getNumOfGrassGrowingDailyValue());
        map.setRandom(new Random());
        map.setUseMutationSwapGene(getUseMutationSwapGeneValue());
        map.setUseLifeGivingCorpses(getUseLifeGivingCorpsesValue());

        map.initBounds();
        map.initPutAnimals();
        map.initPutGrasses(getInitialGrassNumberValue());
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot){
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
    public void onSimulationStartClicked() throws IllegalArgumentException, IOException {
        WorldMap map = new WorldMap();
        settingMap(map);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.show();

        Simulation simulation = new Simulation(map);

        SimulationPresenter presenter = loader.getController();
        map.addObserver(presenter);
        presenter.setSimulation(simulation);
        presenter.setWorldMap(map);
        presenter.initializePresenter();

        stage.setOnCloseRequest(event ->
                stopSimulation(simulation));

        SimulationEngine engine = new SimulationEngine(new ArrayList<>(List.of(simulation)), 4);
        System.out.println("started");
        engine.runAsync();
    }


    private void stopSimulation(Simulation simulation){
        simulation.stopSimulation();
    }


    public void onConfigurationSelected() {
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
