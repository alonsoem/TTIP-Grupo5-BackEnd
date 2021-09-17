package ar.edu.unq.ttip.alec.backend.model.tax;

import javax.persistence.Entity;

@Entity
public class Pais extends Tax {

    private Double secondaryTax;

    public Pais(String name, Double tax, Double secondaryTax) {
        super(name,tax);
        this.secondaryTax=secondaryTax;
    }
    protected Pais(){}

    public Double getSecondaryRate() {
        return secondaryTax;
    }

    @Override
    public Double calculateWith(Double amount, String description) {
        //TODO Otro Strategy aca?
        if (isInApartadoA(description)) {
            return this.getRate()/100*amount;
        }
        if (isInApartadoB(description) && amount<10) {
            return this.getRate()/100*amount;
        }else{
            return this.getSecondaryRate()/100*amount;
        }
    }
}
