package ar.edu.unq.ttip.alec.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Responsable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    protected Responsable(){}

    public Responsable(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
