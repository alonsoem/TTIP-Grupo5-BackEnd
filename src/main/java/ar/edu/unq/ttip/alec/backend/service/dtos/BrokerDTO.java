package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.Tax;

import java.util.List;
import java.util.stream.Collectors;

public class BrokerDTO {
    private Boolean isPublic;
    private String name;
    private String description;
    private Integer id;
    private List<TaxDTO> taxes;
    private Integer userId;
    private String userFullName;

    public BrokerDTO(String name,String description, Boolean isPublic){
        this.name=name;
        this.description=description;
        this.isPublic=isPublic;
    }

    public BrokerDTO(){}


    public Broker toModel(){
        return new Broker(this.name,this.description,this.isPublic);
    }

    public static BrokerDTO fromModel(Broker broker){
        BrokerDTO dto = new BrokerDTO(broker.getName(),broker.getDescription(), broker.isPublic());
        dto.setId(broker.getId());
        dto.setTaxes(broker.getTaxes());
        dto.setUserId(broker.getOwnerId());
        dto.setUserFullName(broker.getOwnerFullName());
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

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDescription() {
        return this.description;
    }
}
