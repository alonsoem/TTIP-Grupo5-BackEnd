package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RegisterDTO {

    @NotBlank(message = "Email address must be provided")
    private String email;
    @NotBlank(message = "password must be provided")
    private String password;
    @NotBlank(message = "Name must be provided")
    private String name;
    @NotNull(message = "Source Id cannot be null")
    private Integer sourceId;

    public RegisterDTO(Integer source,String email, String name, String password) {
        this.password = password;
        this.email=email;
        this.name=name;
        this.sourceId = source;

    }

    public RegisterDTO() { }

    public FrontUser toModel(Source source){
        return new FrontUser(email, name, password,source);
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

    public Integer getSourceId() {
        return sourceId;
    }
}
