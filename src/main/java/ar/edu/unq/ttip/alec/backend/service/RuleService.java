package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.rules.ConditionAction;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.repository.RuleRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxRulesDTO;
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
    private TaxRulesService taxRulesService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
    }

    public List<Rule> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Rule create(Integer taxRuleId, RuleDTO request){
        Rule rule = request.toModel();
        return taxRulesService.addRule(taxRuleId, rule);

    }
}
