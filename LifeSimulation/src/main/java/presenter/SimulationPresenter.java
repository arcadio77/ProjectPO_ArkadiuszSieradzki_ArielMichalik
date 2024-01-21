package presenter;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;

import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import model.interfaces.MapChangeListener;
import model.interfaces.WorldElement;
import model.util.Energy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;


public class SimulationPresenter implements MapChangeListener {

    public Button stopBtn;
    public Label statistics;
    public Label numberOfAllAnimals;
    public Label numberOfAllPlants;
    public Label numberOfEmptyCells;
    public Label averageEnergyLevelForLivingAnimals;
    public Label averageLifespanForDeathAnimals;
    public Label averageKidsNumberForLivingAnimals;
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
    public Label mostPopularGenome;
    public Label trackedAnimalGenome;
    private WorldMap map;
    private Simulation simulation;
    public GridPane gridMap;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    public void initializePresenter(){
        drawMap();
    }

    private void drawMap(){
        clearGrid();
        createGrid(map.getWidth(), map.getHeight());
        putGrasses(map.getHeight());
        putAnimals(map.getHeight());
        updateStatsLabels(map);
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
        numberOfAllAnimals.setText("Number of Animals: " + statistics.getNumberOfAllAnimals());
        numberOfAllPlants.setText("Number of Plants: " + statistics.getNumberOfAllPlants());
        numberOfEmptyCells.setText("Number of Empty Cells: " + statistics.getNumberOfEmptyCells());
        mostPopularGenome.setText("Most Popular Genome: " + statistics.getMostPopularGenome());
        averageEnergyLevelForLivingAnimals.setText("Average Energy: " + statistics.getAverageEnergyLevelForLivingAnimals());
        averageLifespanForDeathAnimals.setText("Average Number of Kids: " + statistics.getAverageLifespanForDeathAnimals());
        averageKidsNumberForLivingAnimals.setText("Average Lifespan of Dead Animals: " + statistics.getAverageKidsNumberForLivingAnimals());

    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }

    public void onStopSimulationClicked() {
        //TODO on stop simulation clicked
    }

    public void onStopTrackingAnimal() {
        //TODO on stop tracking animal clicked
    }

    public void onContinueSimulationClicked() {
        //TODO continue simulation clicked
    }
}
