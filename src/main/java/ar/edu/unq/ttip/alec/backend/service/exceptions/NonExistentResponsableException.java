package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NonExistentResponsableException extends RuntimeException{
    public NonExistentResponsableException(Integer id) {
        super("Non existent responsable with id " + id);
    }
}
