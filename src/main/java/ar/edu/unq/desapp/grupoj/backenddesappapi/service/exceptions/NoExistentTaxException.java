package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;

public class NoExistentTaxException extends Throwable{
    public NoExistentTaxException(Integer id) {
        super("Non existent Tax with id " + id);
    }
}
