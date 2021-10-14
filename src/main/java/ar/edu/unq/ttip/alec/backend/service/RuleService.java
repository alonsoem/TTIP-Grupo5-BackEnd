package ar.edu.unq.ttip.alec.backend.service;

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
import java.util.stream.Collectors;

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
        List<Fact> factList = request.getFactIds().stream().map(each->factService.getFactByName(each)).collect(Collectors.toList());
        Rule rule = request.toModel(factList);
        return taxService.addRule(taxId, rule);

    }

    @Transactional
    public Rule add(Integer taxId, Integer ruleId){
        Rule rule =getRuleById(ruleId);
        return taxService.addRule(taxId, rule);

    }
}
