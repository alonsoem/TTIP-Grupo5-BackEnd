package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NonExistentBrokerException extends RuntimeException {
    public NonExistentBrokerException(Integer id)  {
        super("Non existent broker with id " + id);
    }
}
