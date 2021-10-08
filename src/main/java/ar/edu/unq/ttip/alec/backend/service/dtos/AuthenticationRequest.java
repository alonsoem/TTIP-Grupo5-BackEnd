package ar.edu.unq.ttip.alec.backend.service.dtos;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationRequest() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
