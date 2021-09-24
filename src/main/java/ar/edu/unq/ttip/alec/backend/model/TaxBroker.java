package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaxBroker {

    private List<Tax> taxes= new ArrayList<Tax>();
    private String name ="";

    public TaxBroker (){}
    public TaxBroker (String taxBrokerName){
        name=taxBrokerName;
    }

    public void add(Tax atax){
        taxes.add(atax);
    }

    public List<TaxResult> calculate(BigDecimal amount, Apartado apartado){
        return taxes.stream().map(eachTax -> eachTax.calculateWith(amount,apartado)).collect(Collectors.toList());
    }

    public CalcResultDTO getResults(BigDecimal amount, Apartado apartado) {
        BrokerResult calcResult = new BrokerResult(name, amount, calculate(amount, apartado));
        return calcResult.getResults();
    }

    public List<Tax> getTaxes() {
        return taxes;
    }
}
