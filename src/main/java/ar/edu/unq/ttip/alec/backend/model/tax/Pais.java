package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Pais extends Tax {

    private BigDecimal secondaryTax;

    public Pais(String name, BigDecimal tax, BigDecimal secondaryTax) {
        super(name,tax);
        this.secondaryTax=secondaryTax;
    }
    protected Pais(){}

    public BigDecimal getSecondaryRate() {
        return secondaryTax;
    }

    @Override
    public BigDecimal result(BigDecimal amount, Apartado apartado) {

        if (apartado==Apartado.APARTADOA) {
            return amount.multiply(this.getRate().divide(BigDecimal.valueOf(100)));
        }
        if (apartado==Apartado.APARTADOB && amount.compareTo(BigDecimal.TEN)==-1) { //-1 implica que amount es menor que TEN
            return amount.multiply(this.getRate().divide(BigDecimal.valueOf(100)));
        }else{
            return amount.multiply(this.getSecondaryRate().divide(BigDecimal.valueOf(100)));

        }
    }


}
