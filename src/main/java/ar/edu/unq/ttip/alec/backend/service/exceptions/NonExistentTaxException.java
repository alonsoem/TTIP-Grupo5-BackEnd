package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NonExistentTaxException extends RuntimeException{
    public NonExistentTaxException(Integer id) {
        super("Non existent Tax with id " + id);
    }
}
