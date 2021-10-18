package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NonExistentFactException extends RuntimeException{
    public NonExistentFactException(String name)  {
        super("Non existent fact with id " + name);
    }
}
