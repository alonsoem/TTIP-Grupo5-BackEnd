package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.enumClasses.Apartado;
import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.repository.BrokerCriteriaRepository;
import ar.edu.unq.ttip.alec.backend.repository.BrokerRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.BrokerDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.SearchRequest;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentBrokerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


@Service
public class BrokerService {

    @Autowired
    private BrokerRepository repo;

    @Autowired
    private BrokerCriteriaRepository criteria;

    @Autowired
    private TaxService taxService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private GroupFactService groupFactService;

    public Broker getBrokerById(Integer id) {
        return repo.getBrokerById(id).orElseThrow(() -> new NonExistentBrokerException(id));
    }

    public List<Broker> findAll() {return repo.findAll();}

    public List<Broker> filteredBrokers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();

        return repo.findAllByIsPublicIsTrueOrOwner(userDetails.getUsername());
    }

    @Transactional
    public Broker createBroker(BrokerDTO brokerDto){
        Broker broker = brokerDto.toModel();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();
        broker.setOwner(userDetails);
        repo.save(broker);
        return broker;
    }

    @Transactional
    public Tax addTaxRule(Integer brokerId, Tax tax) {
        Broker broker = getBrokerById(brokerId);
        tax.setBroker(broker);
        broker.add(tax);
        repo.save(broker);
        return tax;
    }

    @Transactional
    public void removeTax(Broker broker, Tax tax) {
        broker.removeTax(tax);
        repo.save(broker);
    }

    public Object calculate(BigDecimal amount, Apartado apartado, Integer brokerId) {
        Broker broker = getBrokerById(brokerId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser userDetails = (FrontUser) auth.getPrincipal();
        //List<Fact> facts = factService.getAllByClass();
        List<Fact> facts = groupFactService.getFactsFromGroups();
        return broker.getResultsWith(amount,apartado,userDetails,facts);

    }

    @Transactional
    public Broker update(Integer id, BrokerDTO brokerDto){
        Broker broker = repo.getBrokerById(id).orElseThrow(() -> new NonExistentBrokerException(id));
        broker.setName(brokerDto.getName());
        broker.setDescription(brokerDto.getDescription());
        broker.setPublic(brokerDto.getIsPublic());
        repo.save(broker);
        return broker;
    }

    @Transactional
    public void remove(Integer id){
        Broker broker = repo.getBrokerById(id).orElseThrow(() -> new NonExistentBrokerException(id));
        repo.delete(broker);
    }

    @Transactional
    public BrokerDTO copyBroker(Integer brokerId) {
        Broker existentBroker= getBrokerById(brokerId);
        Broker newBroker = createBroker(BrokerDTO.fromModel(existentBroker));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        FrontUser user = (FrontUser) auth.getPrincipal();
        newBroker.setOwner(user);
        newBroker.setPublic(false);
        newBroker.setName(newBroker.getName() + " (C)" );


        taxService.copyTaxes(existentBroker,newBroker);

        repo.save(newBroker);
        return BrokerDTO.fromModel(newBroker);
    }


    public List<Broker> getBrokerByDescription(SearchRequest request) {
        return criteria.findAllWithFilters(request.getWords());
    }

    public List<Broker> getAllUserBrokers(Integer userId) {
        return repo.findAllPublicByUser(userId);
    }
}

