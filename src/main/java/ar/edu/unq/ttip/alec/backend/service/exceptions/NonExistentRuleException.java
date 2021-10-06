package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NonExistentRuleException extends RuntimeException{
    public NonExistentRuleException(Integer id) {
        super("Non existent Rule with id " + id);
    }
}
