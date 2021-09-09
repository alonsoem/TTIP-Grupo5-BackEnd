package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NoExistentTaxException extends Throwable{
    public NoExistentTaxException(Integer id) {
        super("Non existent Tax with id " + id);
    }
}
