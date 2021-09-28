package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaxBroker {

    private List<Tax> taxes= new ArrayList<>();
    private List<Rule> rules= new ArrayList<>();
    private String name ="";

    public TaxBroker (){}
    public TaxBroker (String taxBrokerName){
        name=taxBrokerName;
    }

    public void add(Tax atax){
        taxes.add(atax);
    }
    public void add(Rule rule){
        rules.add(rule);
    }

    public List<TaxResult> calculate(BigDecimal amount, Apartado apartado){
        return taxes.stream().map(eachTax -> eachTax.calculateWith(amount,apartado)).collect(Collectors.toList());
    }

    public List<TaxResult> calculateWithRules(BigDecimal amount, Apartado apartado){
        return rules.stream().map(rule -> rule.calculateWith(amount,apartado)).collect(Collectors.toList());
    }

    public CalcResultDTO getResults(BigDecimal amount, Apartado apartado) {
        BrokerResult calcResult = new BrokerResult(name, amount, calculate(amount, apartado));
        return calcResult.getResults();
    }

    public CalcResultDTO getResultsWithRules(BigDecimal amount, Apartado apartado) {
        BrokerResult calcResult = new BrokerResult(name, amount, calculateWithRules(amount, apartado));
        return calcResult.getResults();
    }

    public List<Tax> getTaxes() {
        return taxes;
    }
}
