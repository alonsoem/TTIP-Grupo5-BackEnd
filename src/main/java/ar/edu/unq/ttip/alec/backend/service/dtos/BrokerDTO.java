package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Broker;

public class BrokerDTO {
    private String name;

    public BrokerDTO(String name){ this.name=name;}


    public BrokerDTO(){}


    public Broker toModel(){
        return new Broker(this.name);
    }

    public static BrokerDTO fromModel(Broker broker){
        return new BrokerDTO(broker.getName());
    }

    public String getName() {
        return name;
    }

}
