package ar.edu.unq.ttip.alec.backend.model.rules;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.mvel.MVELRule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Accessors(chain = true,fluent = true)
@Setter
@Getter

public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Integer priority;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConditionAction> when = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConditionAction> then = new ArrayList<>();
    @ManyToMany
    private List<ar.edu.unq.ttip.alec.backend.model.rules.Fact> facts = new ArrayList<>();

    public Rule(){}


    public Rule (String name, String description, Integer priority,
                 List<ConditionAction> when, List<ConditionAction> then,
                 List<ar.edu.unq.ttip.alec.backend.model.rules.Fact> facts){
        this.name=name;
        this.description=description;
        this.priority=priority;
        this.when = when;
        this.then = then;
        this.facts=facts;
    }

    public MVELRule toMVEL(){
        MVELRule rule= new MVELRule()
                .name(name)
                .description(description)
                .priority(priority);
        when.stream().forEach(each ->rule.when(each.value));
        then.stream().forEach(each ->rule.then(each.value));
        return rule;
    }

    public String getName() {
        return name;
    }

    public List<String> getWhen() {
        return when.stream().map(i->i.value).collect(Collectors.toList());
    }

    public List<String> getThen() {
        return then.stream().map(i->i.value).collect(Collectors.toList());
    }

    public List<ar.edu.unq.ttip.alec.backend.model.rules.Fact> getFacts() {return facts;}

    public Integer getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public Integer getId() {
        return id;
    }

    public void addWhen(ConditionAction condition) {
        when.add (condition);
    }
    public void addThen(ConditionAction condition) {
        then.add (condition);
    }
    public void addFact(Fact fact) {
        facts.add (fact);
    }

    public Rule then(String action) {
        this.then.add (new ConditionAction(action));
        return this;
    }
    public Rule when(String action) {
        this.when.add (new ConditionAction(action));
        return this;
    }


    public void setId(Integer id) {
        this.id = id;
    }
}


