package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Tax;

public class TaxDTO {
    private Integer id;
    private String name;

    public TaxDTO(){}

    public TaxDTO(String name){this.name=name;}

    public String getName() {
        return name;
    }

    public Tax toModel(){
        return new Tax(name);
    }

    public Integer getId() {
        return id;
    }
}
