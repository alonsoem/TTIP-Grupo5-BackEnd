package ar.edu.unq.ttip.alec.backend.service.exceptions;

public class SwapRuleException extends RuntimeException {
    public SwapRuleException(Integer ruleIdFrom, Integer ruleIdTo) {
        super("The rules with ids " + ruleIdFrom + ", " + ruleIdTo + " cannot be swapped.");
    }
}
