package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.Tax;

import java.util.List;
import java.util.stream.Collectors;

public class BrokerDTO {
    private String name;
    private Integer id;
    private List<TaxDTO> taxes;

    public BrokerDTO(String name){ this.name=name;}

    public BrokerDTO(){}


    public Broker toModel(){
        return new Broker(this.name);
    }

    public static BrokerDTO fromModel(Broker broker){
        BrokerDTO dto = new BrokerDTO(broker.getName());
        dto.setId(broker.getId());
        dto.setTaxes(broker.getTaxes());
        return dto;
    }

    private void setTaxes(List<Tax> taxes) {
        this.taxes=taxes.stream().map(tax->TaxDTO.fromModel(tax)).collect(Collectors.toList());
    }

    public List<TaxDTO> getTaxes() {
        return taxes;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
