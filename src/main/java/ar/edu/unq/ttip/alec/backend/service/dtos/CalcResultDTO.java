package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.TaxResult;

import java.math.BigDecimal;
import java.util.List;

public class CalcResultDTO {

    private BigDecimal originalAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String taxName;
    private List<TaxResult> detail;

    public CalcResultDTO(String name, BigDecimal originalAmount, BigDecimal taxAmount, BigDecimal totalAmount, List<TaxResult> detail){
        this.originalAmount=originalAmount;
        this.taxAmount=taxAmount;
        this.totalAmount=totalAmount;
        this.taxName=name;
        this.detail=detail;
    }

    protected CalcResultDTO(){}

    public BigDecimal getAmount() {
        return originalAmount;
    }
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    public BigDecimal getTotalAmount(){return totalAmount;}
    public String getTaxName() {
        return taxName;
    }

    public List<TaxResult> getDetail() {
        return detail;
    }
}
