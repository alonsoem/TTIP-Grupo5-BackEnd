package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Language;

public class LanguageDTO {

    private Integer id;
    private String name;

    public LanguageDTO(Integer id, String name){
        this.id=id;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public static LanguageDTO fromModel(Language language){
        return new LanguageDTO(language.getId(),language.getValue());

    }
}
