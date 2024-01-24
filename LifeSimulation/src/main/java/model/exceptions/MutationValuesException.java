package model.exceptions;

public class MutationValuesException extends Exception{
    public MutationValuesException(){
        super("Mutations values are wrong, change at least one of them");
    }
}
