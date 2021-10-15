package ar.edu.unq.ttip.alec.backend.model.rules;

import ar.edu.unq.ttip.alec.backend.model.Rate;

import javax.persistence.*;

@Entity
public class RateFact extends Fact {


    @JoinTable(
            name = "rel_facts_rates",
            joinColumns = @JoinColumn(name = "fk_fact", nullable = false),
            inverseJoinColumns = @JoinColumn(name="fk_rate", nullable = false)
    )
    @ManyToOne
    private Rate rate;

    public RateFact(String name){
        super(name);
        this.fixed=false;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public Rate getRate() {
        return rate;
    }

    protected RateFact(){}

    @Override
    public Object getValue() {
        return this.rate;
    }

}
