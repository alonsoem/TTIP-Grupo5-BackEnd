package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class UserAlreadyExistsException extends Throwable{
    public UserAlreadyExistsException () {
        super("User Already exists");
    }
}
