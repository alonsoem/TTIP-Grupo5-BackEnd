package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxRules> rulesObj= new ArrayList<>();
    private String name ="";

    public Broker(){}
    public Broker(String taxBrokerName){
        name=taxBrokerName;
    }

    public void add(TaxRules rule){
        rulesObj.add(rule);
    }

    public List<TaxResult> calculateWith(BigDecimal amount, Apartado apartado,FrontUser user){
        return rulesObj.stream().map(rule -> rule.calculateWith(amount,apartado,user)).collect(Collectors.toList());
    }


    public CalcResultDTO getResultsWith(BigDecimal amount, Apartado apartado,FrontUser user) {
        BrokerResult calcResult = new BrokerResult(name, amount, calculateWith(amount, apartado,user));
        return calcResult.getResults();
    }

    public List<TaxRules> getRules() {
        return rulesObj;
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
