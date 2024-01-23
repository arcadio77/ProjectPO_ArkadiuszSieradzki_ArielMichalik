package model.util;

import presenter.StarterPresenter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationSaver {
    private StarterPresenter starterPresenter;
    public ConfigurationSaver(StarterPresenter sP){
        this.starterPresenter = sP;
    }

    public void createTXTFile(String filePath, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String rowBuilder = filename + "," +
                    starterPresenter.getSpeedValueString() + "," +
                    starterPresenter.getWidthValue() + "," +
                    starterPresenter.getHeightValue() + "," +
                    starterPresenter.getAnimalNumberValue() + "," +
                    starterPresenter.getInitialAnimalEnergyValue() + "," +
                    starterPresenter.getInitialGrassNumberValue() + "," +
                    starterPresenter.getNumOfGrassGrowingDailyValue() + "," +
                    starterPresenter.getGrassEnergyValue() + "," +
                    starterPresenter.getBreedReadyEnergyValue() + "," +
                    starterPresenter.getBreedLostEnergyValue() + "," +
                    starterPresenter.getGenomeLengthValue() + "," +
                    starterPresenter.getMinMutationNumValue() + "," +
                    starterPresenter.getMaxMutationNumValue() + "," +
                    starterPresenter.getUseMutationSwapGeneValue() + "," +
                    starterPresenter.getUseLifeGivingCorpsesValue() + "," +
                    starterPresenter.getCorpseEffectTimeValue()
                    ;
            writer.write(rowBuilder);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
