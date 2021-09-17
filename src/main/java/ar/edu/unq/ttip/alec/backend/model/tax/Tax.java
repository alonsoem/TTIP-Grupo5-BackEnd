package ar.edu.unq.ttip.alec.backend.model.tax;

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


    public Double calculateWith(Double amount, String description) {
        return rate/100*amount;
    }

    protected Boolean isInApartadoA(String description){
        return true; //TODO Implementar el control
    }
    protected Boolean isInApartadoB(String description){
        return false; //TODO Implementar el control
    }
}
