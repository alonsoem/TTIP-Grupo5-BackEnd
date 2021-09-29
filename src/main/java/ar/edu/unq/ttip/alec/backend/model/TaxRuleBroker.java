package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaxRuleBroker {

    private List<TaxRules> rulesObj= new ArrayList<>();
    private String name ="";

    public TaxRuleBroker(){}
    public TaxRuleBroker(String taxBrokerName){
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
}
