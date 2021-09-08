package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;

public class NonExistentTitleException extends Exception {
    public NonExistentTitleException(Integer titleId) {
        super("There is no Title with ID " + titleId.toString());
    }
}
