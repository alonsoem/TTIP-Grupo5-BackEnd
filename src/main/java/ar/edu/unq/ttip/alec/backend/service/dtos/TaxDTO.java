package ar.edu.unq.ttip.alec.backend.service.dtos;

import ar.edu.unq.ttip.alec.backend.model.Tax;

import java.util.List;
import java.util.stream.Collectors;

public class TaxDTO {
    private Integer id;
    private String name;
    private String url;
    private List<RuleDTO> rules;

    public TaxDTO(){}

    public TaxDTO(String name,String url,List<RuleDTO> rules){
        this.name=name;
        this.url=url;
        this.rules=rules;
    }

    public Tax toModel(){
        return new Tax(name,url);
    }

    public static TaxDTO fromModel(Tax tax){
        TaxDTO dto = new TaxDTO(
                            tax.getName(),
                            tax.getUrl(),
                            tax.getRules().stream().map(e->RuleDTO.fromModel(e)).collect(Collectors.toList()));
        dto.setId(tax.getId());
        return dto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {return url; }

    public List<RuleDTO> getRules() {
        return rules;
    }
}
