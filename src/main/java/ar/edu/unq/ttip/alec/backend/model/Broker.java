package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;
import org.jeasy.rules.api.Facts;

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

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tax> taxes= new ArrayList<>();
    private String name ="";

    public Broker(){}
    public Broker(String taxBrokerName){
        name=taxBrokerName;
    }

    public void add(Tax rule){
        taxes.add(rule);
    }

    public List<TaxResult> calculateWith(Facts facts){
        return taxes.stream().map(rule -> rule.calculateWith(facts)).collect(Collectors.toList());
    }

    public CalcResultDTO getResultsWith(BigDecimal amount, Apartado apartado, FrontUser user,List<Fact> facts) {
        Facts updatedFacts = getFacts(amount, apartado, user,facts);
        BrokerResult calcResult = new BrokerResult(name, amount, calculateWith(updatedFacts));
        return calcResult.getResults();
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Facts getFacts(BigDecimal amount, Apartado apartado, FrontUser user, List<Fact> facts) {
        Facts jeassyFacts = new Facts();
        facts.stream().forEach(eachFact-> {
                jeassyFacts.put(eachFact.getName(),eachFact.getValue());
        });
        //No se persisten, asociados a parametros
        jeassyFacts.put("apartado", apartado);
        jeassyFacts.put("amount", amount);
        jeassyFacts.put("user", user);

        return jeassyFacts;
    }

    public void removeTax(Tax tax) {
        taxes.remove(tax);
    }
}
