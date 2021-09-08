package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;

import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.UserDTO;

public class NonExistentUserException extends Exception {
    public NonExistentUserException(UserDTO user) {
        super("There is no user with UserID " + user.getUserId());
    }
}
