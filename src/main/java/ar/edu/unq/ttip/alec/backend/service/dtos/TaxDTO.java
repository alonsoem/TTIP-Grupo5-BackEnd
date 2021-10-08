package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Tax;

public class TaxDTO {
    private Integer id;
    private String name;
    private String url;

    public TaxDTO(){}

    public TaxDTO(String name,String url){
        this.name=name;
        this.url=url;
    }

    public Tax toModel(){
        return new Tax(name,url);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {return url; }
}
