package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

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
