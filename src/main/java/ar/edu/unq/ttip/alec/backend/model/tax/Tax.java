package ar.edu.unq.ttip.alec.backend.model.tax;

import ar.edu.unq.ttip.alec.backend.model.Apartado;
import ar.edu.unq.ttip.alec.backend.model.TaxResult;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Tax implements TaxStrategy{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private BigDecimal rate;

    public Tax (String name, BigDecimal taxRate){
        this.name=name;
        this.rate=taxRate;
    }

    protected Tax(){}

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public BigDecimal result (BigDecimal amount, Apartado apartado){
        return amount.multiply(rate.divide(new BigDecimal(100)));
    }

    public TaxResult calculateWith(BigDecimal amount, Apartado apartado) {

        return new TaxResult(
                            this.result(amount,apartado),
                            this.id,
                            this.name
                            );
    }


}
