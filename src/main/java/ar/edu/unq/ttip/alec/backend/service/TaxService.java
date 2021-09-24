package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.Apartado;
import ar.edu.unq.ttip.alec.backend.model.TaxBroker;
import ar.edu.unq.ttip.alec.backend.model.TaxResult;
import ar.edu.unq.ttip.alec.backend.model.tax.IVAExterior;
import ar.edu.unq.ttip.alec.backend.model.tax.Pais;
import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.repository.TaxRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalculationDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class TaxService {


    @Autowired
    private TaxRepository repo;
    private TaxBroker broker = new TaxBroker("PAGOS MONEDA EXTRANJERA");




    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save (new Tax("TASA 21%", new BigDecimal(21)));
        repo.save (new Tax("TASA 30%", new BigDecimal(30)));
        repo.save (new IVAExterior("IVA Exterior", BigDecimal.valueOf(21)));
        repo.save (new Pais("Impuesto Pais", BigDecimal.valueOf(30),BigDecimal.valueOf(8)));

        Tax ivaExt = repo.getTaxById(3).orElseThrow(() -> new NonExistentTaxException(3));
        Tax pais= repo.getTaxById(4).orElseThrow(() -> new NonExistentTaxException(4));
        broker.add(pais);
        broker.add(ivaExt);

    }

    public Tax getByTitleId(Integer id) {
        return repo.getTaxById(id).orElseThrow(() -> new NonExistentTaxException(id));
    }

    public List<Tax> findAll() {
        return repo.findAll();
    }

    public CalcResultDTO calculate(BigDecimal amount, Apartado apartado, Integer taxId){

        return broker.getResults(amount,apartado);

        /*Tax tax = this.getByTitleId(taxId);
        BigDecimal calcResult = tax.calculateWith(amount,apartado);

        return new CalcResultDTO(amount, calcResult,taxId);*/
    }


}