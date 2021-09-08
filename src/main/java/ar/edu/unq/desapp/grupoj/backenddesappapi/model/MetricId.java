package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import java.io.Serializable;

public class MetricId implements Serializable {
    private String methodName;

    private Integer platformId;

    // default constructor

    public MetricId(){}
    public MetricId(String methodName, Integer platformId) {
        this.methodName= methodName;
        this.platformId= platformId;
    }

    public boolean equals(MetricId obj) {
        if (this.methodName == obj.methodName && this.platformId==obj.platformId) {return true;};
        if (obj == null) {return false;};
        if (getClass() != obj.getClass()) {return false;};
        return false;
    }

    // equals() and hashCode()
}
