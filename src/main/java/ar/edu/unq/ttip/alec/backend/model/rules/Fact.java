package ar.edu.unq.ttip.alec.backend.model.rules;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

@Entity
public class Fact {
    @Id
    @Column(name="name")
    private String name;
    protected Boolean fixed=true;
    private String description;

    public Fact(String name,String description){
        this.name=name;
        this.description=description;
    }
    protected Fact(){}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return this.description ;
    }

    public Object getValue() {
        return null;
    }
    public Boolean fixed(){return this.fixed;}
}
