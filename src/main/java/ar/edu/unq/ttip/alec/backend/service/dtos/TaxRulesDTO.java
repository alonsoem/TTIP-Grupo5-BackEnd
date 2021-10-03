package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;

public class TaxRulesDTO {
    private Integer id;
    private String name;

    public TaxRulesDTO(){}

    public TaxRulesDTO(String name){this.name=name;}

    public String getName() {
        return name;
    }

    public TaxRules toModel(){
        return new TaxRules(name);
    }

    public Integer getId() {
        return id;
    }
}
