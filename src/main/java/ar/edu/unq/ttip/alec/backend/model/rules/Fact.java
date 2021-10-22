package ar.edu.unq.ttip.alec.backend.model.rules;

import ar.edu.unq.ttip.alec.backend.model.enumClasses.FactType;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;

@Entity
public class Fact {
    @Id
    @Column(name="name")
    private String name;
    protected Boolean fixed=true;
    private String description;
    public FactType type;

    public Fact(String name,String description){
        this.name=name;
        this.description=description;
        this.type = FactType.CORE;
    }
    protected Fact(){}

    public String getName() {
        return name;
    }

    public FactType getType() {
        return this.type;
    }

    public String getDescription() {
        return this.description ;
    }

    public Object getValue() {
        return null;
    }
    public Boolean fixed(){return this.fixed;}
}
