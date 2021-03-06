package ar.edu.unq.ttip.alec.backend.model;


import ar.edu.unq.ttip.alec.backend.model.enumClasses.Province;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "frontuser",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userName"})}
)
@Setter
public class FrontUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String password;
    private String name;

    private Province province;

    private Boolean responsableInscripto = false;

    private Boolean gananciasYBienesP = false;

    private boolean active=true;
    private String roles="USER";

    @Transient
    private List<GrantedAuthority> authorities;


    protected FrontUser(){}

    public FrontUser(String email, String name, String password,
                     Province province, Boolean isRespInscripto, Boolean isGananciasYBienesP){
        this.userName=email;
        this.name=name;
        this.password=password;
        this.province=province;
        this.responsableInscripto=isRespInscripto;
        this.gananciasYBienesP=isGananciasYBienesP;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public Province getProvince() { return province;}

    public Boolean isResponsableInscripto(){return responsableInscripto;}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }


    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {  return name;   }

    public boolean isGananciasYBienesP() { return gananciasYBienesP; }
}

