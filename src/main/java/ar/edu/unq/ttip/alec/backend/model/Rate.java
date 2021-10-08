package ar.edu.unq.ttip.alec.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private BigDecimal rate;

    public Rate(String name, BigDecimal taxRate){
        this.name=name;
        this.rate=taxRate;
    }

    protected Rate(){}

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Integer getId() {
        return id;
    }


}
