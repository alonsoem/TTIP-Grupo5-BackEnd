package ar.edu.unq.desapp.grupoj.backenddesappapi.model;


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

public class FrontUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String password;
    private String name;
    private boolean active=true;
    private String roles="USER";

    @Transient
    private List<GrantedAuthority> authorities;

    @ManyToOne
    private Source source;

    protected FrontUser(){}

    public FrontUser(String email, String name, String password, Source source){
        this.userName=email;
        this.name=name;
        this.password=password;
        this.source=source;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    public void setId(Integer id) {
        this.id = id;
    }
//    public void setSource(Source source) { this.source = source;}


    public String getUsername() {
        return userName;
    }

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

    public String getPlatform() {
        return source.getName();
    }
    public Integer getPlatformId(){
        return source.getId();
    }
}

