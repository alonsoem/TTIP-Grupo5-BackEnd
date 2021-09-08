package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;

import javax.validation.constraints.NotBlank;

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
