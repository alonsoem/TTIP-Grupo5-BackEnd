package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.rules.ClassFact;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.repository.FactRepository;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentFactException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FactService {


    @Autowired
    private FactRepository repo;

    public List<Fact> findAll() {
        return repo.findAll();
    }

    public Fact getFactByName(String name){return repo.getFactByName(name).orElseThrow(()-> new NonExistentFactException(name));}


    public List<Fact> getAllByClass() {
        return repo.getFactsByFixedFalse();
    }
}