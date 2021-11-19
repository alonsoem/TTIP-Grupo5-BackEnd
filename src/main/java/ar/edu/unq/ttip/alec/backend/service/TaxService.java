package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.repository.TaxRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TaxService {

    @Autowired
    private BrokerService brokerService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private TaxRepository repo;

    public Tax getTaxById(Integer id) {
        return repo.getTaxById(id).orElseThrow(() -> new NonExistentTaxException(id));
    }

    public List<Tax> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Tax createTax(Integer brokerId, TaxDTO request){
        Tax tax = request.toModel();
        return brokerService.addTaxRule(brokerId, tax);

    }

    @Transactional
    public Rule addRule(Integer taxId, Rule rule) {
        Tax tax= getTaxById(taxId);
        rule.setPriority(tax.getMaxPriority());
        rule.setTax(tax);
        tax.addRule(rule);
        repo.save(tax);
        return (rule);

    }

    @Transactional
    public Object addTax(Integer brokerId, Integer taxId) {
        Tax tax = getTaxById(taxId);
        tax = brokerService.addTaxRule(brokerId, tax);
        return tax;
    }

    @Transactional
    public Tax update(Integer taxId, TaxDTO request){
        Tax tax = this.getTaxById(taxId);
        tax.setName(request.getName());
        tax.setUrl(request.getUrl());
        repo.save(tax);
        return tax;
    }

    @Transactional
    public void remove(Integer taxId){
        Tax tax = this.getTaxById(taxId);
        Broker broker = tax.getBroker();
        brokerService.removeTax(broker,tax);
        repo.delete(tax);
    }

    @Transactional
    public void removeRule(Tax tax, Rule rule) {
        tax.removeRule(rule);
        repo.save(tax);
    }

    @Transactional
    public void copyTaxes(Broker existentBroker, Broker newBroker) {
        existentBroker.getTaxes().stream().forEach(existentTax ->{
            Tax taxNew = TaxDTO.fromModel(existentTax).toModel();
            taxNew.setId(null);
            taxNew.setBroker(newBroker);
            Tax taxCopy = copyRules(existentTax,taxNew);
            newBroker.add(taxCopy);

        });
    }

    private Tax copyRules(Tax existentTax, Tax taxCopy) {
        existentTax.getRules().stream().forEach(existentRule ->
                {
                    Rule newRule = copyRule(existentRule);
                    newRule.setTax(taxCopy);
                    taxCopy.addRule(newRule);
                }

        );
        return taxCopy;
    }


    private Rule copyRule(Rule rule){
        return new Rule(rule.getName(),
                        rule.getDescription(),
                        rule.getPriority(),
                        rule.getWhen().stream().map(eachWhen->new String(eachWhen)).collect(Collectors.toList()),
                        rule.getThen().stream().map(eachThen->new String(eachThen)).collect(Collectors.toList())
        );
    }


    public Object swap(Integer taxId, Integer ruleIdFrom, Integer ruleIdTo) {
        Tax tax = getTaxById(taxId);
        return tax.swapRules(ruleIdFrom,ruleIdTo);

    }
}

