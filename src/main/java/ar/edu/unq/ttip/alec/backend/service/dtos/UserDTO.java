package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;

public class UserDTO {

    private Integer id;
    private String name;

    protected UserDTO(){}

    public UserDTO(Integer id, String fullName){
        this.id=id;
        this.name=fullName;
    }

    public static UserDTO fromModel(FrontUser user){
        return new UserDTO(user.getId(),user.getName());
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}

