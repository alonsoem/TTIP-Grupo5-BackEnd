package ar.edu.unq.ttip.alec.backend.model.rules;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

@Entity
public class Fact {
    @Id
    @Column(name="name")
    private String name;
    protected Boolean fixed=true;

    public Fact(String name){
        this.name=name;
    }
    protected Fact(){}

    public String getName() {
        return name;
    }

    public Object getValue() {
        return null;
    }
    public Boolean fixed(){return this.fixed;}
}
