package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name = "";
    private Double rate =0.0;

    public Tax (String name, Double taxRate){
        this.name=name;
        this.rate=taxRate;
    }
    protected Tax(){}

    public String getName() {
        return name;
    }

    public Double getRate() {
        return rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
