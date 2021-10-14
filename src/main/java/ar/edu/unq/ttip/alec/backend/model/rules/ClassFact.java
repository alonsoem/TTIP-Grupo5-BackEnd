package ar.edu.unq.ttip.alec.backend.model.rules;


import javax.persistence.Entity;

@Entity
public class ClassFact extends Fact {

    private String className;

    public ClassFact(String name, String className){
        super(name);
        this.className=className;
    }
    protected ClassFact(){}

    public String getClassName() {
        return className;
    }

    @Override
    public Object getValue() throws ClassNotFoundException {
        return Class.forName(this.className);
    }

}
