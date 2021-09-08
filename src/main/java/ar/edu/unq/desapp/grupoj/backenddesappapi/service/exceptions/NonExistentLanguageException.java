package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;


public class NonExistentLanguageException extends Exception {
    public NonExistentLanguageException(Integer languageId) {
        super("There is no Language with ID " + languageId.toString());
    }





}
