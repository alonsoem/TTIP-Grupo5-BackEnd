package ar.edu.unq.ttip.alec.backend.model;

import java.math.BigDecimal;

public class TaxResult {
    private BigDecimal amount;
    private Integer taxId;
    private String taxDescription;
    private String taxUrl;

    public TaxResult(BigDecimal amount, Integer taxId, String taxDescription,String taxUrl){
        this.amount=amount;
        this.taxId=taxId;
        this.taxDescription=taxDescription;
        this.taxUrl=taxUrl;
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

    public String getTaxUrl() {
        return taxUrl;
    }
}
