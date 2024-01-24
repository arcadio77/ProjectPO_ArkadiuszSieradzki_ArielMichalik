package model.exceptions;

public class NonNumericInputException extends Exception {
    public NonNumericInputException(String parameterName){
        super("Input parameter " + parameterName + " contains non-numeric characters.");
    }
}
