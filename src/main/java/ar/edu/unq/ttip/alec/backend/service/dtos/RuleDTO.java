package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.rules.ConditionAction;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RuleDTO {

    private String name;
    private String description;
    private Integer priority;
    private List<String> when = new ArrayList<>();
    private List<String> then = new ArrayList<>();
    private List<String> factIds= new ArrayList<>();

    public RuleDTO(){    }

    public RuleDTO (String name, String description, Integer priority, List<String> then, List<String> when,List<String> factsId){
        this.name=name;
        this.description=description;
        this.priority=priority;
        this.when=when;
        this.then=then;
        this.factIds=factsId;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public List<String> getThen() {
        return then;
    }

    public List<String> getWhen() {
        return when;
    }

    public List<String> getFactIds() {
        return factIds;
    }

    public Rule toModel(List<Fact> facts){

        return new Rule(name,
                description,
                priority,
                when.stream().map(string -> new ConditionAction(string)).collect(Collectors.toList()),
                then.stream().map(string -> new ConditionAction(string)).collect(Collectors.toList()),
                facts
                );

    }
}
