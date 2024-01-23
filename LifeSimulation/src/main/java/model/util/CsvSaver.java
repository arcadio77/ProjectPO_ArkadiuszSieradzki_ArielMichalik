package model.util;

import model.Statistics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvSaver {
    private final Statistics stats;
    public CsvSaver(Statistics stats){
        this.stats = stats;
    }

    public void createCsvFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Day number, Number of animals, Number of plants, Number of empty cells, Most popular genome, " +
                    "Average energy, Average number of children, Average lifespan of dead animals");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRow(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String rowBuilder = stats.getDayNumber() + "," +
                    stats.getNumberOfAllAnimals() + "," +
                    stats.getNumberOfAllPlants() + "," +
                    stats.getNumberOfEmptyCells() + "," +
                    stats.getMostPopularGenome() + "," +
                    stats.getAverageEnergyLevelForLivingAnimals() + "," +
                    stats.getAverageKidsNumberForLivingAnimals() + "," +
                    stats.getAverageLifespanForDeathAnimals() + ",";
            writer.write(rowBuilder);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
