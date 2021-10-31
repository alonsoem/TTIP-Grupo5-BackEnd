package ar.edu.unq.ttip.alec.backend.model.rules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GroupFact {

    @Id
    @Column(name="name")
    private String name;
    private String description;
    @OneToMany
    private List<Fact> facts= new ArrayList<>();

    public GroupFact(String name, String description){
        this.name=name;
        this.description=description;
    }
    protected GroupFact(){}

    public void addFact(Fact fact){ this.facts.add(fact);}

    public List<Fact> getFacts() throws ClassNotFoundException {
        return facts;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
