package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.tax.IVAExterior;
import ar.edu.unq.ttip.alec.backend.model.tax.Pais;
import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.repository.TaxRepository;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaxService {


    @Autowired
    private TaxRepository repo;



    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save (new Tax("TASA 21%", 21.0));
        repo.save (new Tax("TASA 30%", 30.0));
        repo.save (new IVAExterior("IVA Exterior", 21.0));
        repo.save (new Pais("Impuesto Pais", 30.0,8.0));

    }

    public Tax getByTitleId(Integer id) {
        return repo.getTaxById(id).orElseThrow(() -> new NonExistentTaxException(id));
    }

    public List<Tax> findAll() {
        return repo.findAll();
    }

    public Double calculate(Double amount, String description,Integer taxId){

        Tax tax = this.getByTitleId(taxId);
        return tax.calculateWith(amount,description);
    }




}