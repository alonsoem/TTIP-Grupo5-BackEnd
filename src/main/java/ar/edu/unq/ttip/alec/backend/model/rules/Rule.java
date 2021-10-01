package ar.edu.unq.ttip.alec.backend.model.rules;

import org.jeasy.rules.mvel.MVELRule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
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

    public Rule(){    }

    public Rule (String name, String description, Integer priority,List<ConditionAction> when, List<ConditionAction> then){
        this.name=name;
        this.description=description;
        this.priority=priority;
        this.when = when;
        this.then = then;
    }

    public MVELRule toMVEL(){
        MVELRule rule= new MVELRule()
                .name(name)
                .description(description)
                .priority(priority);
        when.stream().forEach(each ->rule.when(each.value));
        then.stream().forEach(each ->rule.when(each.value));
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


}


