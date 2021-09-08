package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;

public class UserAlreadyExistsException extends Throwable{
    public UserAlreadyExistsException () {
        super("User Already exists");
    }
}
