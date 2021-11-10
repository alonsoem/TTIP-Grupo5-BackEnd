package ar.edu.unq.ttip.alec.backend.service.dtos;

import lombok.Getter;

@Getter
public class AuthResponseDTO {

    private Integer id;
    private String token;

    public AuthResponseDTO() {}

    public AuthResponseDTO(Integer id, String token){
        this.id=id;
        this.token=token;
    }


}
