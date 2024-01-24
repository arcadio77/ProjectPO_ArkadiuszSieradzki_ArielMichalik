package model.util;

import model.exceptions.MutationValuesException;
import model.exceptions.NegativeValuesException;
import model.exceptions.NonNumericInputException;
import presenter.StarterPresenter;


public class InputDataValidator {

    public static void validate(StarterPresenter presenter) throws MutationValuesException, NegativeValuesException, NonNumericInputException {
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



}
