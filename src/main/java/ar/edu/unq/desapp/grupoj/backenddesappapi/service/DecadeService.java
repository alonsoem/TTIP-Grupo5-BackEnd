package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Decade;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.DecadeRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentDecadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class DecadeService {

    @Autowired
    private DecadeRepository repo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save(new Decade("D80",1980,1989));
        repo.save(new Decade("D90",1990,1999));
        repo.save(new Decade("D2000",2000,2009));
        repo.save(new Decade("D2010",2010,2019));
        repo.save(new Decade("D2020",2020,2029));

    }

    public List<Decade> findAll() {
        return repo.findAll();
    }

    public Decade getById(String id) throws NonExistentDecadeException {
        return repo.getById(id).orElseThrow(()-> new NonExistentDecadeException(id));
    }



}
