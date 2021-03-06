package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.model.rules.GroupFact;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.repository.RuleRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.SwapRulesDto;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentRuleException;
import ar.edu.unq.ttip.alec.backend.service.exceptions.SwapRuleException;
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
    private GroupFactService groupFactService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {}

    public List<Rule> findAll() {
        return repo.findAll();
    }

    public List<GroupFact> findAllFacts() {
        return groupFactService.findAll();
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


    public Object swap(Integer taxId, Integer ruleIdFrom, Integer ruleIdTo) {
        Rule ruleA = getRuleById(ruleIdFrom);
        Rule ruleB = getRuleById(ruleIdTo);

        if (taxId == ruleA.getTax().getId() && ruleA.getTax().getId() == ruleB.getTax().getId()) {
            Integer priority0 = ruleA.getPriority();
            ruleA.setPriority(ruleB.getPriority());
            ruleB.setPriority(priority0);

            repo.save(ruleA);
            repo.save(ruleB);
            return new SwapRulesDto(ruleIdFrom, ruleIdTo);
        } else {
            throw new SwapRuleException(ruleIdFrom, ruleIdTo);
        }
    }



}
