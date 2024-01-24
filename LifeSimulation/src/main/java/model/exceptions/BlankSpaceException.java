package model.exceptions;

public class BlankSpaceException extends Exception {
    public BlankSpaceException(String parameterName) {
        super("Field " + parameterName + " is empty.");
    }
}