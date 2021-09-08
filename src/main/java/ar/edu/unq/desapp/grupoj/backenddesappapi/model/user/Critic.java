package ar.edu.unq.desapp.grupoj.backenddesappapi.model.user;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.CascadeType;
import java.util.Collection;


@Entity
@Table(
        name = "critic",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"source","critic_id"})}
)
public class Critic  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="source")
    private Source source;

    @Column(name="critic_id")
    private String userId;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Location location;

    protected Critic(){}

    public Critic(Source source, String userId, Location location){

        this.source=source;
        this.userId=userId;
        this.location=location;
    }



    public Source getSource() {
        return source;
    }

    public String getUserId() {
        return userId;
    }

    public String getUniqueIdString(){
        return this.getSource().getName() + " " + getUserId();
    }

    public Integer getSourceId() {
        return this.source.getId();
    }

    public Location getLocation() {
        return location;
    }
}
