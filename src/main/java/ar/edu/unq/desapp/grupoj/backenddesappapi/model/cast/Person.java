package ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Person (String name){
        this.name=name;
    }

    protected Person(){}

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
