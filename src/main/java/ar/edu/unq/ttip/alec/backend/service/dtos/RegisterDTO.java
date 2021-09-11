package ar.edu.unq.ttip.alec.backend.service.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Provincia;
import ar.edu.unq.ttip.alec.backend.model.Responsable;

public class RegisterDTO {

    @NotBlank(message = "Email address must be provided")
    private String email;
    @NotBlank(message = "password must be provided")
    private String password;
    @NotBlank(message = "Name must be provided")
    private String name;

    @NotNull(message = "Province Id cannot be null")
    private Integer provinceId;
    @NotNull(message = "Responsable Id cannot be null")
    private Integer responsableId;

    public RegisterDTO(String email, String name, String password, Integer provinceId) {
        this.password = password;
        this.email=email;
        this.name=name;
        this.provinceId=provinceId;

    }

    public RegisterDTO() { }

    public FrontUser toModel(Provincia province, Responsable responsable){
        return new FrontUser(email, name, password, province,responsable);
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
    public Integer getResponsableId() {
        return responsableId;
    }
}
