package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.repository.TaxRulesRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxRulesDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TaxRulesService {

    @Autowired
    private BrokerService brokerService;

    @Autowired
    private TaxRulesRepository repo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save(new TaxRules("IMPUESTO PAIS"));
        repo.save(new TaxRules("IVA EXTERIOR"));
    }

    public TaxRules getTaxRulesById(Integer id) {
        return repo.getTaxRulesById(id).orElseThrow(() -> new NonExistentTaxException(id));
    }

    public List<TaxRules> findAll() {
        return repo.findAll();
    }

    @Transactional
    public TaxRules createTaxRules(Integer brokerId, TaxRulesDTO request){
        TaxRules taxRules = request.toModel();
        return brokerService.addTaxTule(brokerId, taxRules);
    }

    @Transactional
    public Rule addRule(Integer taxRuleId, Rule rule) {
        TaxRules tax= getTaxRulesById(taxRuleId);
        tax.addRule(rule);
        repo.save(tax);
        return (rule);
    }

    @Transactional
    public Object addTaxRules(Integer brokerId, Integer taxRuleId) {
        TaxRules taxRules = getTaxRulesById(taxRuleId);
        return brokerService.addTaxTule(brokerId, taxRules);
    }
}