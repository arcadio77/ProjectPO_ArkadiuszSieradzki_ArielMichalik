package presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Mutation;

import model.Simulation;
import model.SimulationEngine;
import model.WorldMap;
import model.util.Energy;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StarterPresenter {
    public ComboBox<String> configurations;
    public Button startBtn;
    public TextField getSpeed;
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


    public int getSpeedValue() {return Integer.parseInt(getSpeed.getText());}

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
        map.setInitialAnimalsNumber(getAnimalNumberValue());
        map.setInitialPlantsNumber(getInitialGrassNumberValue());
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

        Simulation simulation = new Simulation(map, getSpeedValue());

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
            getSpeed.setText("800");
            getWidth.setText("10");
            getHeight.setText("10");
            getAnimalNumber.setText("20");
            getInitialAnimalEnergy.setText("10");
            getInitialGrassNumber.setText("20");
            getNumOfGrassGrowingDaily.setText("5");
            getGrassEnergy.setText("3");
            getBreedReadyEnergy.setText("4");
            getBreedLostEnergy.setText("2");
            getGenomeLength.setText("5");
            getMinMutationNum.setText("1");
            getMaxMutationNum.setText("1");
        } else if (selectedConfiguration.equals("Configuration 2")) {
            getSpeed.setText("800");
            getWidth.setText("20");
            getHeight.setText("20");
            getAnimalNumber.setText("30");
            getInitialAnimalEnergy.setText("7");
            getInitialGrassNumber.setText("12");
            getNumOfGrassGrowingDaily.setText("10");
            getGrassEnergy.setText("3");
            getBreedReadyEnergy.setText("4");
            getBreedLostEnergy.setText("2");
            getGenomeLength.setText("8");
            getMinMutationNum.setText("2");
            getMaxMutationNum.setText("2");
            getUseMutationSwapGene.setSelected(true);
            getUseLifeGivingCorpses.setSelected(true);
        }
    }

    public void showLegend() {
        //TODO show legend
    }
}
