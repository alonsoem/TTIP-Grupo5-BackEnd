package ar.edu.unq.ttip.alec.backend.model;

import ar.edu.unq.ttip.alec.backend.model.TaxResult;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;

import java.math.BigDecimal;
import java.util.List;


public class BrokerResult {

    private String name;
    private List<TaxResult> results;
    private BigDecimal originalAmount;

    public BrokerResult(String name, BigDecimal amount, List<TaxResult> calcResults){
        this.name=name;
        this.originalAmount=amount;
        this.results=calcResults;
    }

    public CalcResultDTO getResults(){
        BigDecimal taxTotal = results.stream().map(r->r.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CalcResultDTO(name,
                            originalAmount,
                            taxTotal,
                            originalAmount.add(taxTotal),
                            results);
    }

}
