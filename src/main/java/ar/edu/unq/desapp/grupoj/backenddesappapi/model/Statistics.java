package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(MetricId.class)
public class Statistics {

    @Id
    String methodName="";

    @Id
    Integer platformId;

    Integer invocations=0;

    protected Statistics(){}

    public Statistics(String methodName,Integer sourceId){

        this.methodName=methodName;
        this.platformId=sourceId;
    }

    public void update(){
        this.invocations=this.invocations+1;
    }

    public Integer getInvocations() {
        return invocations;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public String getMethodName() {
        return methodName;
    }
    public String getId(){return methodName;}
}
