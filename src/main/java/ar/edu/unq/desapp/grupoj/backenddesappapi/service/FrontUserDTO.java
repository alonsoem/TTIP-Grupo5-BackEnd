package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import lombok.Getter;

@Getter
public class FrontUserDTO {

    private String username;
    private String name;
    private Boolean active;

    public FrontUserDTO(String username, String name, Boolean active){
        this.username=username;
        this.name=name;
        this.active=active;
    }

    public static FrontUserDTO fromModel(FrontUser userModel){
        return new FrontUserDTO(userModel.getUsername(),userModel.getName(),userModel.isEnabled());
    }
}


//{"id":null,"password":"123456","name":"quique","active":true,"roles":"USER","username":"quique@gmail.com"}