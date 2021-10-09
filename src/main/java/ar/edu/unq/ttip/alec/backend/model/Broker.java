package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado;
import ar.edu.unq.ttip.alec.backend.model.enumClasses.Province;
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

    public CalcResultDTO getResultsWith(BigDecimal amount, Apartado apartado, FrontUser user) {
        Facts facts = new Facts();
        facts.put("apartado", apartado);
        facts.put("apartadoA", Apartado.APARTADOA);
        facts.put("apartadoB", Apartado.APARTADOB);
        facts.put("noApartado", Apartado.NOAPARTADO);
        facts.put("tierraDelFuego", Province.TIERRA_DEL_FUEGO);
        facts.put("caba", Province.CABA);
        facts.put("amount", amount);
        facts.put("user", user);
        BrokerResult calcResult = new BrokerResult(name, amount, calculateWith(facts));
        return calcResult.getResults();
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
