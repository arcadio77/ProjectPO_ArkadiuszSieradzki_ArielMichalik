package presenter;

import javafx.application.Platform;
import javafx.geometry.HPos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import model.*;

import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;
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
    private int cellSize;
    private final static int maxGridWidth = 500;
    private final static int maxGridHeight = 500;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    public void initializePresenter(){
        setAnimalStatsVisible(false);
        this.stats = new Statistics(map);
        calculateCellSize();
        drawMap();

    }

    private void calculateCellSize(){
        int maxCellWidth = maxGridWidth / (map.getWidth() + 1);
        int maxCellHeight = maxGridHeight / (map.getHeight() + 1);
        this.cellSize =  Math.min(maxCellWidth, maxCellHeight);
    }

    private void drawMap(){
        clearGrid();
        createGrid(map.getWidth(), map.getHeight());

        putGrasses(map.getHeight());
        if(map.isUseLifeGivingCorpses()){
            putCorpses(map.getHeight());
        }

        putAnimals(map.getHeight());
        updateStatsLabels();
    }

    private void displayAnimalInfo(Animal animal){
        currentTrackedAnimal = animal;
        updateAnimalStatsLabels(animal);
        setAnimalStatsVisible(true);

    }

    private void putCorpses(int height){
        Map<Vector2d, Integer> recentGraves = map.getRecentGraves();
        List<Vector2d> gravesPositions = new ArrayList<>(recentGraves.keySet());
        for(Vector2d pos: gravesPositions){
            int newX = pos.x() + 1;
            int newY = height - pos.y(); //because I input values inot column from biggest to smallest
            Rectangle rectangle = new Rectangle(newX, newY, 30, 30);
            Color plantColor = new Color(0.1, 0.1, 0.1, 1);
            rectangle.setFill(plantColor);
            gridMap.add(rectangle, newX, newY);
        }
    }


    private void putAnimals(int height) {
        for (WorldElement element : map.getElements()) {
            int newX = element.position().x() + 1;
            int newY = height - (element.position().y()); //because I input values inot column from biggest to smallest
            if (element instanceof Animal animal) {
                Color animalColor;
                if (animal.equals(currentTrackedAnimal)) {
                    animalColor = new Color(0.1, 0.1, 1, 1);
                }
                else if (animal.getEnergy() >= 2 * map.getEnergy().getInitialAnimalEnergy()){
                    animalColor = new Color(1, 1, 0, 1);
                }
                else if (animal.getEnergy() >= map.getEnergy().getInitialAnimalEnergy()) {
                    animalColor = new Color(1, 0.5, 0, 1);
                } else {
                    animalColor = new Color(1, 0, 0, 1);
                }
                Circle circle = new Circle(newX, newY, 15, animalColor);
                circle.setOnMouseClicked(event -> displayAnimalInfo(animal));
                gridMap.add(circle, newX, newY);
            }
        }
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
            Circle circle = new Circle(newX, newY, 15, new Color(0.5, 0.4, 0.7, 1));
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
            //if (!map.isOccupiedByAnimal(element.position())) {
                int newX = element.position().x() + 1;
                int newY = height - (element.position().y()); //because I input values inot column from biggest to smallest
                if (element instanceof Grass) {
                    Rectangle rectangle = new Rectangle(newX, newY, cellSize, cellSize);
                    Color plantColor = new Color(0, 0.6, 0, 1);
                    rectangle.setFill(plantColor);
                    gridMap.add(rectangle, newX, newY);
                }
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
        averageLifespanForDeathAnimals.setText("Average Number of Kids: " + stats.getAverageLifespanForDeathAnimals());
        averageKidsNumberForLivingAnimals.setText("Average Lifespan of Dead Animals: " + stats.getAverageKidsNumberForLivingAnimals());

    }

    private void updateAnimalStatsLabels(Animal animal){
        TrackedAnimalStats animalStats = new TrackedAnimalStats(animal);
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
        showMostPopularGenome();
        drawEquator();
    }



    public void onStopSimulationClicked() {
        map.killAllAnimals();
        onContinueSimulationClicked();
        simulationTerminated.setText("Simulation has been terminated");
    }

    public void onStopTrackingAnimal() {
        currentTrackedAnimal = null;
        setAnimalStatsVisible(false);
    }

    public void onContinueSimulationClicked() {
        simulation.continueSimulation();
    }
}
