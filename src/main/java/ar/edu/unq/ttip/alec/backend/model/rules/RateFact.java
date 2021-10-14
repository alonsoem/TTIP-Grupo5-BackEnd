package ar.edu.unq.ttip.alec.backend.model.rules;

public class RateFact extends Fact {

    private Integer factId;

    public RateFact(String name, Integer factId){
        super(name);
        this.factId=factId;

    }

    public Integer getFactId() {
        return factId;
    }

}
