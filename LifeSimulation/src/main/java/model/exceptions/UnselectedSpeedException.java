package model.exceptions;

public class UnselectedSpeedException extends Exception{
    public UnselectedSpeedException(String parameterName) {
        super("Option for speed hasn't been chosen " + parameterName + ".");
    }
}
