package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.repository.TaxRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TaxService {

    @Autowired
    private BrokerService brokerService;

    @Autowired
    private TaxRepository repo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {}

    public Tax getTaxById(Integer id) {
        return repo.getTaxById(id).orElseThrow(() -> new NonExistentTaxException(id));
    }

    public List<Tax> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Tax createTax(Integer brokerId, TaxDTO request){
        Tax tax = request.toModel();
        return brokerService.addTaxTule(brokerId, tax);
    }

    @Transactional
    public Rule addRule(Integer taxId, Rule rule) {
        Tax tax= getTaxById(taxId);
        tax.addRule(rule);
        repo.save(tax);
        return (rule);
    }

    @Transactional
    public Object addTax(Integer brokerId, Integer taxId) {
        Tax tax = getTaxById(taxId);
        return brokerService.addTaxTule(brokerId, tax);
    }

    @Transactional
    public Tax update(Integer taxId, TaxDTO request){
        Tax tax = this.getTaxById(taxId);
        tax.setName(request.getName());
        tax.setUrl(request.getUrl());
        repo.save(tax);
        return tax;
    }
}