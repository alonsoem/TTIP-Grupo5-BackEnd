package ar.edu.unq.ttip.alec.backend.service.dtos;

import java.math.BigDecimal;

public class CalcResultDTO {

    private BigDecimal originalAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private Integer taxId;

    public CalcResultDTO(BigDecimal amount, BigDecimal taxAmount, Integer taxId){
        this.originalAmount=amount;
        this.taxAmount=taxAmount;
        this.taxId=taxId;
    }

    protected CalcResultDTO(){}

    public BigDecimal getAmount() {
        return originalAmount;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    public BigDecimal getTotalAmount(){return originalAmount.add(taxAmount);}
}
