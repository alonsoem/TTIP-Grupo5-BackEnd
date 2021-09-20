package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

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
    public Double calculateWith(Double amount, Apartado apartado) {

        if (apartado==Apartado.APARTADOA) {
            return this.getRate()/100*amount;
        }
        if (apartado==Apartado.APARTADOB && amount<10) {
            return this.getRate()/100*amount;
        }else{
            return this.getSecondaryRate()/100*amount;
        }
    }
}
