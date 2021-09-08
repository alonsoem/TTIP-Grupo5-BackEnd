package ar.edu.unq.desapp.grupoj.backenddesappapi.model.user;



import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(
        name = "user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"source", "critic_id","userNick"})}
)
public class User extends Critic {

    @Column(name="userNick")
    private String userNick;

    protected User(){}

    public User (Source source, String userId, String userNick, Location location){
        super(source,userId,location);
        this.userNick=userNick;
    }


    public String getUserNick() {
        return userNick;
    }

    @Override
    public String getUniqueIdString(){
        return super.getUniqueIdString() + " " + getUserNick();
    }



}
