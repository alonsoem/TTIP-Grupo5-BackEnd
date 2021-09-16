package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class NonExistentProvinciaException extends RuntimeException{
    public NonExistentProvinciaException(Integer id) {
        super("Non existent provincia with id " + id);
    }
}
