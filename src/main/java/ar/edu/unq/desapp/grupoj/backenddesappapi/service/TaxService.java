package ar.edu.unq.desapp.grupoj.backenddesappapi.service;


import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Tax;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.TaxRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NoExistentTaxException;
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
        repo.save (new Tax("IVA", 21.0));
        repo.save (new Tax("Dolar Solidario", 30.0));
        repo.save (new Tax("Impuesto al aire", 10.0));

    }

    public Tax getByTitleId(Integer id) throws NoExistentTaxException {
        return repo.getTaxById(id).orElseThrow(() -> new NoExistentTaxException(id));
    }

    public List<Tax> findAll() {
        return repo.findAll();
    }




}