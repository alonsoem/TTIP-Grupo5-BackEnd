package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.Rate;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.repository.RateRepository;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentBrokerException;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class RateService {


    @Autowired
    private RateRepository repo;

    public List<Rate> findAll() {
        return repo.findAll();
    }

    public Rate getRateById(Integer rateId) {
        return repo.getRateById(rateId).orElseThrow(()->new NonExistentBrokerException(rateId));
    }
}