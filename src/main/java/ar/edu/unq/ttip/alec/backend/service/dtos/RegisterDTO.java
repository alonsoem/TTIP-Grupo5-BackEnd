package ar.edu.unq.ttip.alec.backend.service.dtos;

import javax.swing.text.StyledEditorKit;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Province;


public class RegisterDTO {

    @NotBlank(message = "Email address must be provided")
    private String email;
    @NotBlank(message = "password must be provided")
    private String password;
    @NotBlank(message = "Name must be provided")
    private String name;
    @NotNull(message = "Province cannot be null")
    private Province province;

    private Boolean respInscripto;
    private Boolean gananciasYBienesP;

    public RegisterDTO(String email, String name, String password, Province aProvince, Boolean respInscripto,Boolean ganancias) {
        this.password = password;
        this.email=email;
        this.name=name;
        this.province=aProvince;
        this.respInscripto=respInscripto;
        this.gananciasYBienesP=ganancias;

    }

    protected RegisterDTO() { }

    public FrontUser toModel(){
        return new FrontUser(email, name, password, province, respInscripto,gananciasYBienesP);
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

    public Province getProvince() { return province; }
    public Boolean getRespInscripto() { return respInscripto;}

    public Boolean getGananciasYBienesP() {
        return gananciasYBienesP;
    }
}
