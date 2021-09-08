package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;

public class SourceDTO {

    private Integer id;
    private String name;

    public SourceDTO(Integer id, String name){
        this.id =id;
        this.name=name;
    }

    public static SourceDTO fromModel(Source source){
        return new SourceDTO(source.getId(),source.getName());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
