package ar.edu.unq.ttip.alec.backend.service.dtos;

public class CalculationDTO {

    private Double amount;
    private String description;
    private Integer taxId;

    public CalculationDTO(Double amount, String description, Integer taxId){
        this.amount=amount;
        this.description=description;
        this.taxId=taxId;
    }

    protected CalculationDTO(){}

    public Double getAmount() {
        return amount;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public String getDescription() {
        return description;
    }
}
