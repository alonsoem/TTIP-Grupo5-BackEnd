package ar.edu.unq.ttip.alec.backend.service.dtos;

import lombok.Getter;

@Getter
public class SwapRulesDto {
    private Integer ruleIdFrom;
    private Integer ruleIdTo;

    protected SwapRulesDto(){}

    public SwapRulesDto(Integer ruleIdFrom, Integer ruleIdTo){
        this.ruleIdFrom=ruleIdFrom;
        this.ruleIdTo=ruleIdTo;
    }
}
