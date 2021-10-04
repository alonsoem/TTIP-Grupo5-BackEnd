package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.rules.ConditionAction;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.repository.RuleRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxRulesDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentRuleException;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;
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

        Rule noApartadoPais = new Rule()
                .name("Sin Apartado")
                .description("Verifica que apartado sea ninguno y aplica 0%")
                .priority(1)
                .when("apartado==noApartado")
                .then("result.value=amount*30/100;");

        Rule apartadoARule = new Rule()
                .name("Es Apartado A")
                .description("Verifica que apartado sea igual A y aplica 21%")
                .when("apartado==apartadoA")
                .then("result.value=amount*8/100;")
                .priority(1);

        Rule apartadoBMenorRule = new Rule()
                .name("Es Apartado B y monto menor a 10")
                .description("Verifica que apartado sea igual B y aplica 8%")
                .priority(2)
                .when("apartado==apartadoB")
                .when("amount<10")
                .then("result.value=amount*8/100;");
        Rule apartadoBMayorRule = new Rule()
                .name("Es Apartado B y monto mayor a 10")
                .description("Verifica que apartado sea igual B y aplica 30%")
                .priority(3)
                .when("apartado==apartadoB")
                .when("amount>=10")
                .then("result.value=amount*30/100;");
        repo.save(apartadoARule);
        repo.save(apartadoBMayorRule);
        repo.save(apartadoBMenorRule);
        repo.save(noApartadoPais);
    }

    public List<Rule> findAll() {
        return repo.findAll();
    }

    public Rule getRuleById(Integer id) {
        return repo.getRuleById(id).orElseThrow(() -> new NonExistentRuleException(id));
    }

    @Transactional
    public Rule create(Integer taxRuleId, RuleDTO request){
        Rule rule = request.toModel();
        return taxRulesService.addRule(taxRuleId, rule);

    }

    @Transactional
    public Rule add(Integer taxRuleId, Integer ruleId){
        Rule rule =getRuleById(ruleId);
        return taxRulesService.addRule(taxRuleId, rule);

    }
}
