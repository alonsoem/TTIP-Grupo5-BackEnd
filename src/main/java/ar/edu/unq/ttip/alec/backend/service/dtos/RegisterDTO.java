package ar.edu.unq.ttip.alec.backend.service.dtos;

import javax.validation.constraints.NotBlank;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;

public class RegisterDTO {

    @NotBlank(message = "Email address must be provided")
    private String email;
    @NotBlank(message = "password must be provided")
    private String password;
    @NotBlank(message = "Name must be provided")
    private String name;

    public RegisterDTO(String email, String name, String password) {
        this.password = password;
        this.email=email;
        this.name=name;

    }

    public RegisterDTO() { }

    public FrontUser toModel(){
        return new FrontUser(email, name, password);
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


}
