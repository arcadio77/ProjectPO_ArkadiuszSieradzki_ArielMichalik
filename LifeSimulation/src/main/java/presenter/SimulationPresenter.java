package presenter;

import javafx.application.Platform;
import javafx.geometry.HPos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.*;
import model.*;

import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class SimulationPresenter implements MapChangeListener {

    public Button pauseBtn;
    public Button stopBtn;
    public Label statisticsLabel;
    public Label day;
    public Label numberOfAllAnimals;
    public Label numberOfAllPlants;
    public Label numberOfEmptyCells;
    public Label averageEnergyLevelForLivingAnimals;
    public Label averageLifespanForDeathAnimals;
    public Label averageKidsNumberForLivingAnimals;
    public Label mostPopularGenome;
    public Label trackedAnimalStats;
    public Label trackedAnimalActiveGene;
    public Label trackedAnimalEnergyLevel;
    public Label trackedAnimalEatenPlantsNumber;
    public Label trackedAnimalDescendentsNumber;
    public Label trackedAnimalKidsNumber;
    public Label trackedAnimalHowManyDaysIsLiving;
    public Label trackedAnimalDayOfDeath;
    public Label dayOfSimulation;
    public Button stopTrackingBtn;
    public Button continueBtn;
    public Label trackedAnimalGenome;
    private WorldMap map;
    private Simulation simulation;
    public GridPane gridMap;
    public Label simulationTerminated;
    private Statistics stats;
    Animal currentTrackedAnimal = null;
    private Animal previousTrackedAnimal = null;
    private int cellSize;
    private int radiusValue;
    private final static int maxGridWidth = 700;
    private final static int maxGridHeight = 700;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    public void setStats(Statistics statistics) {
        this.stats = statistics;
    }

    public void initializePresenter(){
        setAnimalStatsVisible(false);
        calculateCellSize();
        calculateRadiusValue();
        drawMap();

    }

    private void calculateCellSize(){
        int maxCellWidth = maxGridWidth / (map.getWidth() + 1);
        int maxCellHeight = maxGridHeight / (map.getHeight() + 1);
        this.cellSize =  Math.min(maxCellWidth, maxCellHeight);
    }


    private void calculateRadiusValue(){
        this.radiusValue = (int)(2*cellSize/5);
    }

    private void drawMap(){
        if(!simulation.checkIfRunning()) onTerminateSimulationClicked();
        clearGrid();
        createGrid(map.getWidth(), map.getHeight());

        putGrasses(map.getHeight());
        if(map.isUseLifeGivingCorpses()){
            putCorpses(map.getHeight());
        }

        putAnimals();
        updateStatsLabels();
        printTrackedAnimal();
    }

    private void printTrackedAnimal(){
        if(currentTrackedAnimal != null){
            if(currentTrackedAnimal.getDeathDate() == 0 && simulation.checkIfRunning()) {
                int newX = currentTrackedAnimal.position().x() + 1;
                int newY = map.getHeight() - (currentTrackedAnimal.position().y());
                Color animalColor = new Color(0.1, 0.9, 1, 1);
                Circle circle = new Circle(newX, newY, radiusValue, animalColor);
                GridPane.setHalignment(circle, HPos.CENTER);
                gridMap.add(circle, newX, newY);
            }
            updateAnimalStatsLabels();
        }
    }

    private void putCorpses(int height){
        Map<Vector2d, Integer> recentGraves = map.getRecentGraves();
        List<Vector2d> gravesPositions = new ArrayList<>(recentGraves.keySet());
        for(Vector2d pos: gravesPositions){
            int newX = pos.x() + 1;
            int newY = height - pos.y();
            Rectangle rectangle = new Rectangle(newX, newY, cellSize,  cellSize);
            Color plantColor;
            if(map.isOccupiedByGrass(pos)){
                plantColor = new Color(0.537, 0.59, 0, 1);
            }
            else{
                plantColor = new Color(0.537, 0.254, 0, 1);
            }
            rectangle.setFill(plantColor);
            GridPane.setHalignment(rectangle, HPos.CENTER);
            gridMap.add(rectangle, newX, newY);
        }
    }

    private void putAnimals() {
        ArrayList<Animal> bestAnimals = new ArrayList<>(map.getBestAnimals().values());
        for (Animal a : bestAnimals) {
                updateAnimalColor(a);
        }
    }
    private void updateAnimalColor(@NotNull Animal animal){
        int newX = animal.position().x() + 1;
        int newY = map.getHeight() - (animal.position().y());
        Color animalColor;
        if (animal.equals(currentTrackedAnimal)){
           animalColor = new Color(0.1, 0.9, 1, 1);
        }
        else if (animal.getEnergy() >= 2 * map.getEnergy().getInitialAnimalEnergy()){
            animalColor = new Color(1, 1, 0, 1);
        }
        else if (animal.getEnergy() >= map.getEnergy().getInitialAnimalEnergy()) {
            animalColor = new Color(1, 0.5, 0, 1);
        } else {
            animalColor = new Color(1, 0, 0, 1);
        }
        Circle circle = new Circle(newX, newY, radiusValue, animalColor);
        circle.setOnMouseClicked(event -> displayAnimalInfo(animal));
        GridPane.setHalignment(circle, HPos.CENTER);
        gridMap.add(circle, newX, newY);
    }

    private void displayAnimalInfo(Animal animal){
        if (currentTrackedAnimal != null) {
            previousTrackedAnimal = currentTrackedAnimal.clone();
            if(previousTrackedAnimal.getGenomeList().equals(stats.getMostPopularGenome())){
                showMostPopularGenome();
            }
            else{
                updateAnimalColor(previousTrackedAnimal);
            }
        }
        currentTrackedAnimal = animal;
        updateAnimalColor(currentTrackedAnimal);
        updateAnimalStatsLabels();
        setAnimalStatsVisible(true);
    }

    private void showMostPopularGenome() {
        int height = map.getHeight();
        List<Animal> AnimalsWithMostOccuredGenome = map.getElements().stream()
                .filter(element -> element instanceof Animal)
                .map(element -> (Animal) element)
                .filter(animal -> animal.getGenomeList().equals(stats.getMostPopularGenome()))
                .toList();

        for(Animal animal: AnimalsWithMostOccuredGenome){
            int newX = animal.position().x() + 1;
            int newY = height - (animal.position().y());
            Circle circle = new Circle(newX, newY, radiusValue, new Color(0.5, 0.4, 0.7, 1));
            circle.setOnMouseClicked(event -> displayAnimalInfo(animal));
            GridPane.setHalignment(circle, HPos.CENTER);
            gridMap.add(circle, newX, newY);
        }
    }

    private void drawEquator() {
        Boundary equatorBounds = map.getJungleBounds();
        for (int y = equatorBounds.lowerLeft().y(); y <= equatorBounds.upperRight().y(); y++){
            for (int x = 0; x <= map.getWidth()-1; x++){
                Rectangle rectangle = new Rectangle(cellSize, cellSize, Color.LIGHTGREEN);
                GridPane.setHalignment(rectangle, HPos.CENTER);
                gridMap.add(rectangle, x + 1, map.getHeight() - y);
            }
        }
    }

    private void putGrasses(int height) {
        for(WorldElement element: map.getElements()){
                int newX = element.position().x() + 1;
                int newY = height - (element.position().y());
                if (element instanceof Grass) {
                    Rectangle rectangle = new Rectangle(newX, newY, cellSize, cellSize);
                    Color plantColor = new Color(0, 0.6, 0, 1);
                    rectangle.setFill(plantColor);
                    GridPane.setHalignment(rectangle, HPos.CENTER);
                    gridMap.add(rectangle, newX, newY);
                }
        }
    }

    private void clearGrid() {
        gridMap.getChildren().retainAll(gridMap.getChildren().get(0));
        gridMap.getColumnConstraints().clear();
        gridMap.getRowConstraints().clear();
    }

    private void createGrid(int width, int height) {
        Label separator = new Label("y\\x");
        gridMap.add(separator, 0, 0);
        GridPane.setHalignment(separator, HPos.CENTER);
        gridMap.getColumnConstraints().add(new ColumnConstraints(cellSize));
        gridMap.getRowConstraints().add(new RowConstraints(cellSize));

        for(int i=0; i < width; i++){
            gridMap.getColumnConstraints().add(new ColumnConstraints(cellSize));
            Label digit = new Label(String.valueOf(i));
            gridMap.add(digit, i+1, 0); //row
            GridPane.setHalignment(digit, HPos.CENTER);
        }
        for(int i=0; i < height; i++){
            gridMap.getRowConstraints().add(new RowConstraints(cellSize));
            Label digit = new Label(String.valueOf(height-i-1));
            gridMap.add(digit, 0, i+1);
            GridPane.setHalignment(digit, HPos.CENTER);
        }
    }

    private void updateStatsLabels(){
        this.stats.updateStats();
        day.setText("Day: " + stats.getDayNumber());
        numberOfAllAnimals.setText("Number of Animals: " + stats.getNumberOfAllAnimals());
        numberOfAllPlants.setText("Number of Plants: " + stats.getNumberOfAllPlants());
        numberOfEmptyCells.setText("Number of Empty Cells: " + stats.getNumberOfEmptyCells());
        mostPopularGenome.setText("Most Popular Genome: " + stats.getMostPopularGenome());
        averageEnergyLevelForLivingAnimals.setText("Average Energy: " + stats.getAverageEnergyLevelForLivingAnimals());
        averageLifespanForDeathAnimals.setText("Average Lifespan for dead animals : " + String.format(Locale.US, "%.2f",stats.getAverageLifespanForDeathAnimals()));
        averageKidsNumberForLivingAnimals.setText("Average Number of Kids: " + stats.getAverageKidsNumberForLivingAnimals());
    }

    private void updateAnimalStatsLabels(){
        TrackedAnimalStats animalStats = new TrackedAnimalStats(currentTrackedAnimal);
        animalStats.updateStats();
        trackedAnimalGenome.setText("Genome: " + animalStats.getGenome());
        trackedAnimalActiveGene.setText("Active gene: " + animalStats.getActiveGene());
        trackedAnimalEnergyLevel.setText("Energy level: " + animalStats.getEnergyLevel());
        trackedAnimalEatenPlantsNumber.setText("Eaten plants number: " + animalStats.getEatenPlantsNumber());
        trackedAnimalKidsNumber.setText("Kids number: " + animalStats.getKidsNumber());
        trackedAnimalDescendentsNumber.setText("Descendents number: " + animalStats.getDescendantsNumber());
        trackedAnimalHowManyDaysIsLiving.setText("Living days: " + animalStats.getHowManyDaysIsLiving());
        trackedAnimalDayOfDeath.setText("Day of death: " + animalStats.getAnimalDayOfDeath());
    }

    public void setAnimalStatsVisible(boolean isVisible){
        trackedAnimalStats.setVisible(isVisible);
        trackedAnimalGenome.setVisible(isVisible);
        trackedAnimalActiveGene.setVisible(isVisible);
        trackedAnimalEnergyLevel.setVisible(isVisible);
        trackedAnimalEatenPlantsNumber.setVisible(isVisible);
        trackedAnimalKidsNumber.setVisible(isVisible);
        trackedAnimalDescendentsNumber.setVisible(isVisible);
        trackedAnimalHowManyDaysIsLiving.setVisible(isVisible);
        trackedAnimalDayOfDeath.setVisible(isVisible);
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }

    public void onPauseSimulationClicked() {
        simulation.pauseSimulation();
        putGrasses(map.getHeight());
        if(!map.isUseLifeGivingCorpses()){
            drawEquator();
        }
        else{
            putCorpses(map.getHeight());
        }
        putAnimals();
        stats.updateStats();
        showMostPopularGenome();
        printTrackedAnimal();
    }

    public void onTerminateSimulationClicked() {
        onPauseSimulationClicked();
        map.killAllAnimalsAndAllPlantsAndCorpses();
        clearGrid();
        simulationTerminated.setText("Simulation has been terminated");
    }

    public void onStopTrackingAnimalClicked() {
        currentTrackedAnimal = null;
        setAnimalStatsVisible(false);
    }

    public void onContinueSimulationClicked() {
        simulation.continueSimulation();
    }
}
