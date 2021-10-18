package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class FactLoadFailedException extends RuntimeException {
    public FactLoadFailedException()  {
        super("Fact Load Exception!");
    }
}
