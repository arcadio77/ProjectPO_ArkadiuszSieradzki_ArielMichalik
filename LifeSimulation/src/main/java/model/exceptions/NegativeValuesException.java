package model.exceptions;

public class NegativeValuesException extends Exception {
    public NegativeValuesException(String parameterName){
        super("Input value for " + parameterName + " is negative, change it");
    }
}