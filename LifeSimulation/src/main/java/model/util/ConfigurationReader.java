package model.util;

import presenter.StarterPresenter;

import java.io.*;

public class ConfigurationReader {
    private StarterPresenter starterPresenter;

    public ConfigurationReader(StarterPresenter sP) {
        this.starterPresenter = sP;
    }

    public String[] readFromTXTFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                return line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[0];
    }
}

//    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//        String rowBuilder = starterPresenter.getSpeedValueString() + "," +
//                starterPresenter.getWidthValue() + "," +
//                starterPresenter.getHeightValue() + "," +
//                starterPresenter.getAnimalNumberValue() + "," +
//                starterPresenter.getInitialAnimalEnergyValue() + "," +
//                starterPresenter.getInitialGrassNumberValue() + "," +
//                starterPresenter.getNumOfGrassGrowingDailyValue() + "," +
//                starterPresenter.getGrassEnergyValue() + "," +
//                starterPresenter.getBreedReadyEnergyValue() + "," +
//                starterPresenter.getBreedLostEnergyValue() + "," +
//                starterPresenter.getGenomeLengthValue() + "," +
//                starterPresenter.getMinMutationNumValue() + "," +
//                starterPresenter.getMaxMutationNumValue() + ",";
//        writer.write(rowBuilder);
//        writer.newLine();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }