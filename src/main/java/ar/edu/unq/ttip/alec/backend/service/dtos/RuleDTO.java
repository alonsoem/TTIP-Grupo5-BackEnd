package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.rules.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer priority;
    private List<String> when = new ArrayList<>();
    private List<String> then = new ArrayList<>();

    public RuleDTO(){    }

    public RuleDTO (String name, String description, Integer priority, List<String> then, List<String> when){
        this.name=name;
        this.description=description;
        this.priority=priority;
        this.when=when;
        this.then=then;
    }

    public void setId(Integer id){this.id=id;}

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

    public Rule toModel(){

        return new Rule(name,
                description,
                priority,
                when,
                then);

    }

    public static RuleDTO fromModel(Rule rule){
        RuleDTO dto = new RuleDTO(rule.getName(),rule.getDescription(),rule.getPriority(),rule.getThen(),rule.getWhen());
        dto.setId(rule.getId());
        return dto;
    }

    public Integer getId() {
        return id;
    }
}
