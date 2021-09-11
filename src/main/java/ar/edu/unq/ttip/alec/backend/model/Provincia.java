package ar.edu.unq.ttip.alec.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    protected Provincia(){}

    public Provincia(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
