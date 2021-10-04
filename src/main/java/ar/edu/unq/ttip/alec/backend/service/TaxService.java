package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.*;

import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.model.tax.IVAExterior;
import ar.edu.unq.ttip.alec.backend.model.tax.Pais;
import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.repository.TaxRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;
import org.jeasy.rules.mvel.MVELRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class TaxService {


    @Autowired
    private TaxRepository repo;
    private Broker ruleBroker = new Broker("PAGOS MONEDA EXTRANJERA CON RULE");


    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        //Se mantienen por compatibilidad
        repo.save (new Tax("TASA 21%", new BigDecimal(21)));
        repo.save (new Tax("TASA 30%", new BigDecimal(30)));
        repo.save (new IVAExterior("IVA Exterior", BigDecimal.valueOf(21)));
        repo.save (new Pais("Impuesto Pais", BigDecimal.valueOf(8),BigDecimal.valueOf(30)));




        TaxRules rule= new TaxRules("IMPUESTO PAIS");

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


        Rule noApartado = new Rule()
                .name("Sin Apartado")
                .description("Verifica que apartado sea ninguno y aplica 0%")
                .priority(1)
                .when("apartado==noApartado")
                .then("result.value=0;");

        rule.addRule(apartadoARule);
        rule.addRule(apartadoBMenorRule);
        rule.addRule(apartadoBMayorRule);
        rule.addRule(noApartado);
        ruleBroker.add(rule);



        TaxRules taxRulesIva = new TaxRules("IVA EXTERIOR");



        Rule tierraDelFuego = new Rule()
                .name("Es de Tierra del fuego")
                .description("Verifica que Si el usuario es de Tierra del fuego no aplica impuesto.")
                .priority(1)
                .when("user.getProvince()==tierraDelFuego")
                .then("result.value=0;");



        Rule responsableInscripto = new Rule()
                .name("Es Responsable Inscripto")
                .description("Verifica que Si el usuario es RI no aplica impuesto.")
                .priority(2)
                .when("user.isResponsableInscripto()")
                .then("result.value=0;");

        Rule apartadoA = new Rule()
                .name("Apartado A IVA")
                .description("Verifica que si el apartado es A y aplica 21%.")
                .priority(3)
                .when("apartado=apartadoA")
                .then("result.value=amount*21/100;");

        Rule apartadoBMayorADiez = new Rule()
                .name("Apartado B mayor a 10")
                .description("Verifica que si el apartado es B y monto >= 10 aplica 0%.")
                .priority(3)
                .when("apartado=apartadoB")
                .when("amount>=10")
                .then("result.value=0;");


        Rule apartadoBMenorADiez = new Rule()
                .name("Apartado B menor a 10")
                .description("Verifica que si el apartado es B y monto < 10 aplica 21%.")
                .priority(4)
                .when("apartado=apartadoB")
                .when("amount<10")
                .then("result.value=amount*21/100;");

        taxRulesIva.addRule(noApartado);
        taxRulesIva.addRule(tierraDelFuego);
        taxRulesIva.addRule(responsableInscripto);
        taxRulesIva.addRule(apartadoBMayorADiez);
        taxRulesIva.addRule(apartadoBMenorADiez);

        ruleBroker.add(taxRulesIva);


    }

    public List<Tax> findAll() {
        return repo.findAll();
    }

    public CalcResultDTO calculate(BigDecimal amount, Apartado apartado, Integer taxId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();
        return ruleBroker.getResultsWith(amount,apartado,userDetails);
    }



}