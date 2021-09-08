package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    protected Source(){}

    public Source(String platformName){
        this.name=platformName;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }


}
