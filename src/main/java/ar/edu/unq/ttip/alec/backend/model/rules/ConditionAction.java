package ar.edu.unq.ttip.alec.backend.model.rules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConditionAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String value;

    public ConditionAction(String value){
        this.value=value;
    }
    public ConditionAction(){}

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
