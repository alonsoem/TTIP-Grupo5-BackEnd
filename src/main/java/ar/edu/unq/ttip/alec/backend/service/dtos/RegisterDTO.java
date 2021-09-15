package ar.edu.unq.ttip.alec.backend.service.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Provincia;


public class RegisterDTO {

    @NotBlank(message = "Email address must be provided")
    private String email;
    @NotBlank(message = "password must be provided")
    private String password;
    @NotBlank(message = "Name must be provided")
    private String name;
    @NotNull(message = "Province Id cannot be null")
    private Integer provinceId;

    private Boolean respInscripto;

    public RegisterDTO(String email, String name, String password, Integer provinceId, Boolean respInscripto) {
        this.password = password;
        this.email=email;
        this.name=name;
        this.provinceId=provinceId;
        this.respInscripto=respInscripto;

    }

    protected RegisterDTO() { }

    public FrontUser toModel(Provincia province){
        return new FrontUser(email, name, password, province, respInscripto);
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

    public Integer getProvinceId() {
        return provinceId;
    }
    public Boolean getRespInscripto() { return respInscripto;}
}
