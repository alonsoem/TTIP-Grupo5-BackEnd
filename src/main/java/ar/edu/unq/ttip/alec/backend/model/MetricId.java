package ar.edu.unq.ttip.alec.backend.model;

import java.io.Serializable;

public class MetricId implements Serializable {
    private Integer brokerId;

    public MetricId(){}
    public MetricId(Integer brokerId) {
        this.brokerId=brokerId;
    }

    public boolean equals(MetricId obj) {
        if (this.brokerId == obj.brokerId) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        return false;
    }

}
