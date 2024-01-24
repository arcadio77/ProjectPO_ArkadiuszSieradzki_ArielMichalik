package model.util;

import model.exceptions.*;
import presenter.StarterPresenter;


public class InputDataValidator {

    public static void validate(StarterPresenter presenter) throws MutationValuesException, NegativeValuesException, NonNumericInputException, BlankSpaceException, UnselectedSpeedException {

        validateBlankInput("width", presenter.getWidth.getText());
        validateBlankInput("height", presenter.getHeight.getText());
        validateBlankInput("animal number", presenter.getAnimalNumber.getText());
        validateBlankInput("initial grass number", presenter.getInitialGrassNumber.getText());
        validateBlankInput("initial animal energy", presenter.getInitialAnimalEnergy.getText());
        validateBlankInput("number of grass growing daily", presenter.getNumOfGrassGrowingDaily.getText());
        validateBlankInput("grass energy", presenter.getGrassEnergy.getText());
        validateBlankInput("breed lost energy", presenter.getBreedLostEnergy.getText());
        validateBlankInput("breed ready energy", presenter.getBreedReadyEnergy.getText());
        validateBlankInput("genome length", presenter.getGenomeLength.getText());
        validateBlankInput("min mutation number", presenter.getMinMutationNum.getText());
        validateBlankInput("max mutation number", presenter.getMaxMutationNum.getText());
        validateBlankInput("corpse effect time", presenter.getCorpseEffectTime.getText());

        validateComboBox(presenter.getSpeedBox().getSelectionModel().getSelectedItem());
        System.out.println(presenter.getSpeedBox().getItems());

        validateNumericInput("width", presenter.getWidth.getText());
        validateNumericInput("height", presenter.getHeight.getText());
        validateNumericInput("animal number", presenter.getAnimalNumber.getText());
        validateNumericInput("initial grass number", presenter.getInitialGrassNumber.getText());
        validateNumericInput("initial animal energy", presenter.getInitialAnimalEnergy.getText());
        validateNumericInput("number of grass growing daily", presenter.getNumOfGrassGrowingDaily.getText());
        validateNumericInput("grass energy", presenter.getGrassEnergy.getText());
        validateNumericInput("breed lost energy", presenter.getBreedLostEnergy.getText());
        validateNumericInput("breed ready energy", presenter.getBreedReadyEnergy.getText());
        validateNumericInput("genome length", presenter.getGenomeLength.getText());
        validateNumericInput("min mutation number", presenter.getMinMutationNum.getText());
        validateNumericInput("max mutation number", presenter.getMaxMutationNum.getText());
        validateNumericInput("corpse effect time", presenter.getCorpseEffectTime.getText());

        validateNegativeInput("width", presenter.getWidthValue());
        validateNegativeInput("height", presenter.getHeightValue());
        validateNegativeInput("animal number", presenter.getAnimalNumberValue());
        validateNegativeInput("initial grass number", presenter.getInitialGrassNumberValue());
        validateNegativeInput("initial animal energy", presenter.getInitialAnimalEnergyValue());
        validateNegativeInput("number of grass growing daily", presenter.getNumOfGrassGrowingDailyValue());
        validateNegativeInput("grass energy", presenter.getGrassEnergyValue());
        validateNegativeInput("breed lost energy", presenter.getBreedLostEnergyValue());
        validateNegativeInput("breed ready energy", presenter.getBreedReadyEnergyValue());
        validateNegativeInput("genome length", presenter.getGenomeLengthValue());
        validateNegativeInput("min mutation number", presenter.getMinMutationNumValue());
        validateNegativeInput("max mutation number", presenter.getMaxMutationNumValue());
        validateNegativeInput("corpse effect time", presenter.getCorpseEffectTimeValue());

        if (presenter.getMaxMutationNumValue() < presenter.getMinMutationNumValue() || presenter.getMinMutationNumValue() > presenter.getMaxMutationNumValue()) {
            throw new MutationValuesException();
        }

    }
    private static void validateNumericInput(String parameterName, String value) throws NonNumericInputException {
        if (!value.matches("-?\\d+")) {
            throw new NonNumericInputException(parameterName);
        }
    }
    public static void validateNegativeInput(String parameterName, int value) throws NegativeValuesException {
        if (value < 0) {
            throw new NegativeValuesException(parameterName);
        }
    }
    private static void validateBlankInput(String parameterName, String value) throws BlankSpaceException {
        if (value == null || value.trim().isEmpty()) {
            throw new BlankSpaceException(parameterName);
        }
    }

    private static void validateComboBox(Object items) throws UnselectedSpeedException {
        if (items == null) {
            throw new UnselectedSpeedException("speed");
        }
    }
}
