package presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

import model.util.ConfigurationReader;
import model.util.ConfigurationSaver;
import model.util.Energy;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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
    public TextField getCorpseEffectTime;
    public Button addNewConfBtn;
    public Button loadBtn;
    private int speedValue;
    boolean saveCsv;
    public ComboBox speedBox;
    private ObservableMap<String, String[]> configurationsData = FXCollections.observableHashMap();
    public ArrayList<String> filenames = new ArrayList<>();
    public int filenameIdx = 0;

    public String getSpeedValueString() {
        Map<Integer, String> speedValues = Map.of(
                2000, "Turtle speed",
                 1000, "Very Slow",
                 800, "Slow",
                 500, "Optimal",
                 300, "Fast",
                 100, "Very Fast",
                 70, "Faster",
                 55, "Supersonic"
        );
        return speedValues.get(speedValue);
    }

    public int getSpeedValueInt() {
        return speedValue;
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

    public int getCorpseEffectTimeValue() {return Integer.parseInt(getCorpseEffectTime.getText());}


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
        map.setCorpseEffectTime(getCorpseEffectTimeValue());
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

        Simulation simulation = new Simulation(map, getSpeedValueInt(), statistics, filename);

        SimulationPresenter presenter = loader.getController();
        map.addObserver(presenter);
        presenter.setSimulation(simulation);
        presenter.setWorldMap(map);
        presenter.setStats(statistics);
        presenter.initializePresenter();

        stage.setOnCloseRequest(event ->
                pauseSimulation(simulation));

        SimulationEngine engine = new SimulationEngine(new ArrayList<>(List.of(simulation)), 4);
        engine.runAsync();
    }

    public void iterateThroughTxtFiles() {
        String directoryPath = "SavedConfigurations/";
        
        DirectoryStream<Path> stream = null;
        try {
            stream = Files.newDirectoryStream(Paths.get(directoryPath), "*.txt");
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    readConfigurationFromTxt(String.valueOf(entry));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void readConfigurationFromTxt(String filename) throws IOException {
        System.out.println(filename);
        System.out.println("odczytałem");

        ConfigurationReader cR = new ConfigurationReader();
        String[] values = cR.readFromTXTFile(filename);
        if(!configurationsData.containsKey(values[0])){
            addConfiguarationFromFileToSpinBox(values);
        }
        System.out.println(Arrays.toString(values));
    }

    public void addConfiguarationFromFileToSpinBox(String [] values) {
        configurations.getItems().add(values[0]);
        configurationsData.put(values[0], values);
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

    private void pauseSimulation(Simulation simulation){
        simulation.pauseSimulation();
    }

    public void addNewConfiguration() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New configuration name");
        dialog.setHeaderText(null);
        dialog.setContentText("Input new configuration name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            this.filenames.add(filenameIdx,name);
        });
        saveConfigurationToTxt(filenames.get(filenameIdx));
        this.filenameIdx += 1;
    }

    public void saveConfigurationToTxt(String filename) {
        String filePath = null;
        ConfigurationSaver cS = new ConfigurationSaver(this);
        if (filename != null){
            filePath = "SavedConfigurations/" + filename + ".txt";
            cS.createTXTFile(filePath, filename);
        }
    }

    public void onConfigurationSelected() {
        String selectedConfiguration = configurations.getValue();
        String[] values = configurationsData.get(selectedConfiguration);
        if (values != null) {
            updateConfiguration(values);
        }
    }

    private void updateConfiguration(String[] values) {
        speedBox.setValue(values[1]);
        getWidth.setText(values[2]);
        getHeight.setText(values[3]);
        getAnimalNumber.setText(values[4]);
        getInitialAnimalEnergy.setText(values[5]);
        getInitialGrassNumber.setText(values[6]);
        getNumOfGrassGrowingDaily.setText(values[7]);
        getGrassEnergy.setText(values[8]);
        getBreedReadyEnergy.setText(values[9]);
        getBreedLostEnergy.setText(values[10]);
        getGenomeLength.setText(values[11]);
        getMinMutationNum.setText(values[12]);
        getMaxMutationNum.setText(values[13]);
        getUseMutationSwapGene.setSelected(Boolean.parseBoolean(values[14]));
        getUseLifeGivingCorpses.setSelected(Boolean.parseBoolean(values[15]));
        getCorpseEffectTime.setText(values[16]);
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
