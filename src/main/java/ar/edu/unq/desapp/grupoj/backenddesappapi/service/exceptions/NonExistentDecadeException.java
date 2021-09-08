package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;


public class NonExistentDecadeException extends Exception {
    public NonExistentDecadeException(String id) {
        super("There is no Decade with ID " + id);
    }

}
