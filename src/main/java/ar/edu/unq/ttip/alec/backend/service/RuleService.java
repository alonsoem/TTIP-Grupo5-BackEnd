package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.repository.RuleRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentRuleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RuleService {

    @Autowired
    private RuleRepository repo;
    @Autowired
    private TaxService taxService;
    @Autowired
    private FactService factService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {}

    public List<Rule> findAll() {
        return repo.findAll();
    }

    public List<Fact> findAllFacts() {
        return factService.findAll();
    }

    public Rule getRuleById(Integer id) {
        return repo.getRuleById(id).orElseThrow(() -> new NonExistentRuleException(id));
    }

    @Transactional
    public Rule create(Integer taxId, RuleDTO request){
        Rule rule = request.toModel();
        return taxService.addRule(taxId, rule);
    }


    @Transactional
    public Rule add(Integer taxId, Integer ruleId){
        Rule rule =getRuleById(ruleId);
        return taxService.addRule(taxId, rule);
    }

    @Transactional
    public Rule update(Integer ruleId, RuleDTO request){
        Rule rule = getRuleById(ruleId);
        rule.setName(request.getName());
        rule.setDescription(request.getDescription());
        rule.setPriority(request.getPriority());
        rule.setWhen(request.getWhen());
        rule.setThen(request.getThen());
        repo.save(rule);
        return rule;

    }

    @Transactional
    public void remove(Integer ruleId){
        Rule rule = getRuleById(ruleId);
        Tax tax = rule.getTax();
        taxService.removeRule(tax,rule);
        repo.delete(rule);
    }

}
