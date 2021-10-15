package ar.edu.unq.ttip.alec.backend.model.rules;


import ar.edu.unq.ttip.alec.backend.service.exceptions.FactLoadFailedException;

import javax.persistence.Entity;

@Entity
public class ClassFact extends Fact {

    private String className;

    public ClassFact(String name, String className){
        super(name);
        this.className=className;
        this.fixed=false;
    }
    protected ClassFact(){}

    public String getClassName() {
        return className;
    }

    @Override
    public Object getValue() {
        try {
            return Class.forName(this.className);
        } catch (ClassNotFoundException e) {
            throw new FactLoadFailedException();
        }
    }
    @Override
    public Boolean fixed(){return this.fixed;}

}
