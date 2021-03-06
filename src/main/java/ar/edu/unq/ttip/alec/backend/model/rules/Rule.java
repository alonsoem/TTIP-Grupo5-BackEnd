package ar.edu.unq.ttip.alec.backend.model.rules;

import ar.edu.unq.ttip.alec.backend.model.Tax;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jeasy.rules.mvel.MVELRule;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



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

    @ManyToOne
    private Tax tax;

    @ElementCollection(targetClass=String.class)
    private List<String> whenBis;
    @ElementCollection(targetClass=String.class)
    private List<String> thenBis ;

    public Rule(){}


    public Rule (String name, String description, Integer priority,
                 List<String> when, List<String> then){
        this.name=name;
        this.description=description;
        this.priority=priority;
        this.whenBis = when;
        this.thenBis = then;
    }

    public MVELRule toMVEL(){

        MVELRule rule= new MVELRule()
                .name(name)
                .description(description)
                .priority(priority);


        rule.when(this.collectWhenConditions(whenBis));
        thenBis.stream().forEach(each -> rule.then("result.value=" + each + ";"));
        return rule;
    }

    private String collectWhenConditions(List<String> whens){
        return String.join(" && ", whens);
    }

    public String getName() {
        return name;
    }

    public List<String> getWhen() {
        return whenBis;
    }

    public List<String> getThen() {
        return thenBis;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setWhen(List<String> when) {
        this.whenBis = when;
    }

    public void setThen(List<String> then) {
        this.thenBis = then;
    }

    public Rule when(String action) {
        this.whenBis.add (action);
        return this;
    }
    public Rule then(String action) {
        this.thenBis.add (action);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }
}


