package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.*;
import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.model.tax.IVAExterior;
import ar.edu.unq.ttip.alec.backend.model.tax.Pais;
import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.repository.TaxRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;

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
    private TaxRuleBroker ruleBroker = new TaxRuleBroker("PAGOS MONEDA EXTRANJERA CON RULE");


    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        //TODO EStos taxes se podrian eliminar
        repo.save (new Tax("TASA 21%", new BigDecimal(21)));
        repo.save (new Tax("TASA 30%", new BigDecimal(30)));
        repo.save (new IVAExterior("IVA Exterior", BigDecimal.valueOf(21)));
        repo.save (new Pais("Impuesto Pais", BigDecimal.valueOf(8),BigDecimal.valueOf(30)));




        TaxRules rule= new TaxRules("IMPUESTO PAIS");

        MVELRule apartadoARule = new MVELRule()
                .name("Es Apartado A")
                .description("Verifica que apartado sea igual A y aplica 21%")
                .priority(1)
                .when("apartado==apartadoA")
                .then("result.value=amount*8/100;");

        MVELRule apartadoBMenorRule = new MVELRule()
                .name("Es Apartado B y monto menor a 10")
                .description("Verifica que apartado sea igual B y aplica 8%")
                .priority(2)
                .when("apartado==apartadoB")
                .when("amount<10")
                .then("result.value=amount*8/100;");

        MVELRule noApartado = new MVELRule()
                .name("Sin Apartado")
                .description("Verifica que apartado sea ninguno y aplica 0%")
                .priority(1)
                .when("apartado==noApartado")
                .then("result.value=0;");

        rule.addRule(apartadoARule);
        rule.addRule(apartadoBMenorRule);
        rule.addRule(noApartado);
        ruleBroker.add(rule);



        TaxRules taxRulesIva = new TaxRules("IVA EXTERIOR");



        MVELRule tierraDelFuego = new MVELRule()
                .name("Es de Tierra del fuego")
                .description("Verifica que Si el usuario es de Tierra del fuego no aplica impuesto.")
                .priority(1)
                .when("user.getProvince()==tierraDelFuego")
                .then("result.value=0;");


        MVELRule responsableInscripto = new MVELRule()
                .name("Es Responsable Inscripto")
                .description("Verifica que Si el usuario es RI no aplica impuesto.")
                .priority(1)
                .when("user.isResponsableInscripto()")
                .then("result.value=0;");

        MVELRule apartadoBMayorADiez = new MVELRule()
                .name("Apartado B mayor a 10")
                .description("Verifica que si el apartado es B y monto >= 10 aplica 0%.")
                .priority(2)
                .when("apartado=apartadoB")
                .when("amount>=10")
                .then("result.value=0;");


        MVELRule apartadoBMenorADiez = new MVELRule()
                .name("Apartado B menor a 10")
                .description("Verifica que si el apartado es B y monto < 10 aplica 21%.")
                .priority(2)
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

    public Tax getByTitleId(Integer id) {
        return repo.getTaxById(id).orElseThrow(() -> new NonExistentTaxException(id));
    }

    public List<Tax> findAll() {
        return repo.findAll();
    }

    public CalcResultDTO calculate(BigDecimal amount, Apartado apartado, Integer taxId){
        //TODO Seleccionar un TaxRuleBroker por id con taxid y llamar a getResults
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();
        return ruleBroker.getResultsWith(amount,apartado,userDetails);
    }


}