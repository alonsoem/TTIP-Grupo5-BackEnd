package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Suscription;

public class SuscriptionDTO {
    private Integer titleId;
    private String url;

    public SuscriptionDTO(){}

    public SuscriptionDTO(Integer titleId, String url){
        this.url = url;
        this.titleId = titleId;
    }

    public Integer getTitleId() {
        return titleId;
    }

    public String getUrl(){
        return url;
    }

    public SuscriptionDTO fromModel(Suscription suscription){
        return new SuscriptionDTO(suscription.getTitleId(),suscription.getUrl());
    }

    public Suscription toModel(Integer titleId, String url) {
        return new Suscription(titleId, url);
    }
}
