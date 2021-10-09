package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.Tax;

import java.util.List;

public class BrokerDTO {
    private String name;
    private List<Tax> taxes;
    private Integer id;

    public BrokerDTO(String name){ this.name=name;}

    public BrokerDTO(){}

    public Broker toModel(){
        return new Broker(this.name);
    }

    public static BrokerDTO fromModel(Broker broker){
        BrokerDTO dto = new BrokerDTO(broker.getName());
        dto.setTaxes(broker.getTaxes());
        dto.setId(broker.getId());

        return dto;
    }

    protected void setTaxes(List<Tax> taxes) {
        this.taxes=taxes;
    }
    protected void setId(Integer id){this.id=id;}

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }
}
