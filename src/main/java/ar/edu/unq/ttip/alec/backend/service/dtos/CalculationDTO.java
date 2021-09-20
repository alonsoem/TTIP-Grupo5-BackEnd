package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Apartado;

public class CalculationDTO {

    private Double amount;
    private Apartado apartado;
    private Integer taxId;

    public CalculationDTO(Double amount, Apartado apartado, Integer taxId){
        this.amount=amount;
        this.apartado=apartado;
        this.taxId=taxId;
    }

    protected CalculationDTO(){}

    public Double getAmount() {
        return amount;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public Apartado getApartado() {
        return apartado;
    }
}
