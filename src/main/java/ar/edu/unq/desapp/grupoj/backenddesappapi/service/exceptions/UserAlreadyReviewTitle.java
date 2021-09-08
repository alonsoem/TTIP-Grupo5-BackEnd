package ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions;

public class UserAlreadyReviewTitle extends Exception {
    public UserAlreadyReviewTitle(Integer titleId, String uniqueIdString) {
        super("User already review titleId" + titleId + "("+uniqueIdString+")");
    }
}
