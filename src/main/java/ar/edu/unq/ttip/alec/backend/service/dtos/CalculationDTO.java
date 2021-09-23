package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

import java.math.BigDecimal;

public class CalculationDTO {

    private BigDecimal amount;
    private Apartado apartado;
    private Integer taxId;

    public CalculationDTO(BigDecimal amount, Apartado apartado, Integer taxId){
        this.amount=amount;
        this.apartado=apartado;
        this.taxId=taxId;
    }

    protected CalculationDTO(){}

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public Apartado getApartado() {
        return apartado;
    }
}
