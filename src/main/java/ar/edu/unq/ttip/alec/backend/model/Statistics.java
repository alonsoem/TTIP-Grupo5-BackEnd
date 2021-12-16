package ar.edu.unq.ttip.alec.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MetricId.class)
public class Statistics {

    @Id
    Integer brokerId;

    Integer invocations=0;

    protected Statistics(){}

    public Statistics(Integer brokerId){
       this.brokerId=brokerId;
    }

    public void update(){
        this.invocations=this.invocations+1;
    }

    public Integer getInvocations() {
        return invocations;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

}
