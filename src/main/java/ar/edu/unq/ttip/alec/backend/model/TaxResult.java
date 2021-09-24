package ar.edu.unq.ttip.alec.backend.model;

import java.math.BigDecimal;

public class TaxResult {
    private BigDecimal amount;
    private Integer taxId;
    private String taxDescription;

    public TaxResult(BigDecimal amount, Integer taxId, String taxDescription){
        this.amount=amount;
        this.taxId=taxId;
        this.taxDescription=taxDescription;
    }

    protected TaxResult(){}

    public Integer getTaxId() {
        return taxId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTaxDescription() {
        return taxDescription;
    }
}
