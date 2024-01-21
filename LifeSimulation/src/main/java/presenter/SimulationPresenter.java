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


public class SimulationPresenter implements MapChangeListener {

    public Button pauseBtn;
    public Button stopBtn;
    public Label statistics;
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
    Animal currentTrackedAnimal = null;
    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    public void initializePresenter(){
//        gridMap.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        setAnimalStatsVisible(false);
        drawMap();
    }

    private void drawMap(){
        clearGrid();
        createGrid(map.getWidth(), map.getHeight());
        putGrasses(map.getHeight());
        putAnimals(map.getHeight());
        updateStatsLabels(map);
    }

    private void displayAnimalInfo(Animal animal){
        currentTrackedAnimal = animal;
        updateAnimalStatsLabels(animal);
        setAnimalStatsVisible(true);

    }


    private void putAnimals(int height) {
        for (WorldElement element : map.getElements()) {
            int newX = element.position().x() + 1;
            int newY = height - (element.position().y()); //because I input values inot column from biggest to smallest
            if (element instanceof Animal animal) {
                if (animal.getEnergy() >= 2 * map.getEnergy().getInitialAnimalEnergy()){
                    Color animalColor = new Color(1, 1, 0, 1);
                    Circle circle = new Circle(newX, newY, 15, animalColor);
                    gridMap.add(circle, newX, newY);
                } else if (animal.getEnergy() >= map.getEnergy().getInitialAnimalEnergy()) {
                    Color animalColor = new Color(1, 0.5, 0, 1);
                    Circle circle = new Circle(newX, newY, 15, animalColor);
                    gridMap.add(circle, newX, newY);
                } else {
                    Color animalColor = new Color(1, 0, 0, 1);
                    Circle circle = new Circle(newX, newY, 15, animalColor);
                    gridMap.add(circle, newX, newY);
                }
            }
        }
    }

    private void putGrasses(int height) {
        for(WorldElement element: map.getElements()){
            //if (!map.isOccupiedByAnimal(element.position())) {
                int newX = element.position().x() + 1;
                int newY = height - (element.position().y()); //because I input values inot column from biggest to smallest
                if (element instanceof Grass) {
                    Rectangle rectangle = new Rectangle(newX, newY, 30, 30);
                    Color plantColor = new Color(0, 0.8, 0, 1);
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
    private void updateStatsLabels(WorldMap map){
        Statistics statistics = new Statistics(map);
        statistics.updateStats();
        day.setText("Day: " + statistics.getDayNumber());
        numberOfAllAnimals.setText("Number of Animals: " + statistics.getNumberOfAllAnimals());
        numberOfAllPlants.setText("Number of Plants: " + statistics.getNumberOfAllPlants());
        numberOfEmptyCells.setText("Number of Empty Cells: " + statistics.getNumberOfEmptyCells());
        mostPopularGenome.setText("Most Popular Genome: " + statistics.getMostPopularGenome());
        averageEnergyLevelForLivingAnimals.setText("Average Energy: " + statistics.getAverageEnergyLevelForLivingAnimals());
        averageLifespanForDeathAnimals.setText("Average Number of Kids: " + statistics.getAverageLifespanForDeathAnimals());
        averageKidsNumberForLivingAnimals.setText("Average Lifespan of Dead Animals: " + statistics.getAverageKidsNumberForLivingAnimals());

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
