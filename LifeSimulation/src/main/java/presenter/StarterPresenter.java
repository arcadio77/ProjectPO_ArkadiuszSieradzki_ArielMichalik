package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import model.util.Energy;


import java.io.IOException;
import java.util.*;

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
    public Button addNewConfBtn;
    private int speedValue;
    boolean saveCsv;
    public ComboBox speedBox;

    public int getSpeedValue() {return speedValue;}

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

    public int getBreedReadyEnergyValue() { return Integer.parseInt(getBreedReadyEnergy.getText()); }

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


    private void settingMap(WorldMap map){
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
        String filename = saveStatsToCsv();

        WorldMap map = new WorldMap();
        settingMap(map);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.show();
        Statistics statistics = new Statistics(map);

        Simulation simulation = new Simulation(map, getSpeedValue(), statistics, filename);

        SimulationPresenter presenter = loader.getController();
        map.addObserver(presenter);
        presenter.setSimulation(simulation);
        presenter.setWorldMap(map);
        presenter.setStats(statistics);
        presenter.initializePresenter();

        stage.setOnCloseRequest(event ->
                stopSimulation(simulation));

        SimulationEngine engine = new SimulationEngine(new ArrayList<>(List.of(simulation)), 4);
        engine.runAsync();
    }

    public String saveStatsToCsv() {
        saveCsv = false;
        String filename = "output";
        TextInputDialog dialog = new TextInputDialog(filename);
        dialog.setTitle("Save statistics");
        dialog.setHeaderText("Do you want to save statistics");
        dialog.setContentText("Filename:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            saveCsv = true;
            filename = result.get();
        }else{
            filename = null;
        }
        return filename;

    }
    private void stopSimulation(Simulation simulation){
        simulation.pauseSimulation();
    }

    public void addNewConfiguration() {
        //todo
    }

    public void onConfigurationSelected() {
        String selectedConfiguration = configurations.getValue();
        if (selectedConfiguration.equals("Configuration 1")) {
            speedBox.setValue("Optimal");
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
            speedBox.setValue("Very Slow");
            getWidth.setText("15");
            getHeight.setText("15");
            getAnimalNumber.setText("40");
            getInitialAnimalEnergy.setText("7");
            getInitialGrassNumber.setText("42");
            getNumOfGrassGrowingDaily.setText("10");
            getGrassEnergy.setText("6");
            getBreedReadyEnergy.setText("4");
            getBreedLostEnergy.setText("2");
            getGenomeLength.setText("8");
            getMinMutationNum.setText("2");
            getMaxMutationNum.setText("2");
            getUseMutationSwapGene.setSelected(true);
            getUseLifeGivingCorpses.setSelected(true);
        }
        else if (selectedConfiguration.equals("Configuration 3")) {
            speedBox.setValue("Fast");
            getWidth.setText("15");
            getHeight.setText("15");
            getAnimalNumber.setText("40");
            getInitialAnimalEnergy.setText("10");
            getInitialGrassNumber.setText("30");
            getNumOfGrassGrowingDaily.setText("15");
            getGrassEnergy.setText("8");
            getBreedReadyEnergy.setText("4");
            getBreedLostEnergy.setText("3");
            getGenomeLength.setText("14");
            getMinMutationNum.setText("0");
            getMaxMutationNum.setText("2");
            getUseMutationSwapGene.setSelected(false);
            getUseLifeGivingCorpses.setSelected(false);
        }
        else if (selectedConfiguration.equals("Configuration 4")) {
            speedBox.setValue("Optimal");
            getWidth.setText("15");
            getHeight.setText("15");
            getAnimalNumber.setText("70");
            getInitialAnimalEnergy.setText("10");
            getInitialGrassNumber.setText("40");
            getNumOfGrassGrowingDaily.setText("40");
            getGrassEnergy.setText("8");
            getBreedReadyEnergy.setText("10");
            getBreedLostEnergy.setText("7");
            getGenomeLength.setText("15");
            getMinMutationNum.setText("0");
            getMaxMutationNum.setText("2");
            getUseMutationSwapGene.setSelected(true);
            getUseLifeGivingCorpses.setSelected(true);
        }
    }
    public void onSpeedSelected() {
        Map<String, Integer> speedValues = Map.of(
                "Turtle speed", 2000,
                "Very Slow", 1000,
                "Slow", 800,
                "Optimal", 500,
                "Fast", 300,
                "Very Fast", 100,
                "Faster", 70,
                "Supersonic", 55
        );
        String selectedSpeed = (String) speedBox.getValue();
        this.speedValue = speedValues.get(selectedSpeed);
        }

    public void showLegend() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("legend.fxml"));
        Stage stage = new Stage();
        VBox viewRoot = loader.load();
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Legend");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
        stage.show();
    }



}
