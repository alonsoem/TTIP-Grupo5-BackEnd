package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import lombok.Getter;

@Getter
public class FrontUserDTO {

    private String username;
    private String name;
    private Boolean active;
    private Integer provinceId;
    private Boolean isResponsableInscripto;

    public FrontUserDTO(String username, String name, Integer provinceId, boolean isResponsableInscripto, Boolean active){
        this.username=username;
        this.name=name;
        this.active=active;
        this.provinceId=provinceId;
        this.isResponsableInscripto=isResponsableInscripto;
    }

    public static FrontUserDTO fromModel(FrontUser userModel){
        return new FrontUserDTO(userModel.getUsername(),userModel.getName(),userModel.getProvince().getId(),userModel.isResponsableInscripto(),userModel.isEnabled());
    }
}
