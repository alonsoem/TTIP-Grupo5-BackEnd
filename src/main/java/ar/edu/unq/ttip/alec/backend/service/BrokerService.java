package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.Apartado;
import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.repository.BrokerRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.BrokerDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentBrokerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class BrokerService {


    @Autowired
    private BrokerRepository repo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save(new Broker("Broker Pagos Exterior"));
    }

    public Broker getBrokerById(Integer id) {
        return repo.getBrokerById(id).orElseThrow(() -> new NonExistentBrokerException(id));
    }

    public List<Broker> findAll() {
        return repo.findAll();
    }

    @Transactional
    public Broker createBroker(BrokerDTO brokerDto){
        Broker broker = brokerDto.toModel();
        repo.save(broker);
        return broker;
    }

    @Transactional
    public TaxRules addTaxTule(Integer brokerId, TaxRules taxRules) {
        Broker broker = getBrokerById(brokerId);
        broker.add(taxRules);
        repo.save(broker);
        return taxRules;
    }


    public Object calculate(BigDecimal amount, Apartado apartado, Integer brokerId) {
        Broker broker = getBrokerById(brokerId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();
        return broker.getResultsWith(amount,apartado,userDetails);
    }

}

