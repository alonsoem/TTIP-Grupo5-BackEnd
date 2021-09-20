package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tax implements TaxStrategy{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Double rate;

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


    public Double calculateWith(Double amount, Apartado apartado) {
        return rate/100*amount;
    }


}
