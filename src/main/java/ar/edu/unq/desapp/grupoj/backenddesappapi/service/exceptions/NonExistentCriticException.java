package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;

public class NonExistentCriticException extends Exception{
    public NonExistentCriticException(Source source, String criticId) {
        super("There is no user with CriticId" + criticId + " with Source " + source.getName());
    }
}
