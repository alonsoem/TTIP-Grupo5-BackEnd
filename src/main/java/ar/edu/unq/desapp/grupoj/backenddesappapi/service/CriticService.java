package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.CriticRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentCriticException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentLocationException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentSourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CriticService {

    @Autowired
    private CriticRepository repo;

    @Autowired
    private SourceService sourceSrvc;

    @Autowired
    private LocationService locationSrvc;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        Source source = new Source("Netflix-2");
        Location location= new Location("Argentina", "Bahia Blanca");
        repo.save(new Critic(source,"ventura", location));
    }


    @Transactional
    Critic getCritic(Integer sourceId, String criticId) throws NonExistentSourceException, NonExistentCriticException {
        Source source = sourceSrvc.getById(sourceId);
        Critic critic=repo.findBySourceAndUserId(source,criticId).orElseThrow(() -> new NonExistentCriticException(source,criticId));
        return critic;
    }

    @Transactional
    Critic createCritic(Integer sourceId, String userId, Integer locationId) throws NonExistentLocationException, NonExistentSourceException {
        Location location= locationSrvc.getById(locationId);
        Source source = sourceSrvc.getById(sourceId);
        Critic critic = new Critic(source,userId,location);
        repo.save(critic);
        return critic;
    }

    @Transactional
    public Critic getBySourceAndCriticId(Integer sourceId, String criticId, Integer locationId) throws NonExistentCriticException, NonExistentSourceException, NonExistentLocationException {
        Critic critic;
        try {
            critic= this.getCritic(sourceId, criticId);
        }catch (NonExistentCriticException e) {
            critic= this.createCritic(sourceId, criticId, locationId);
        }
        repo.save(critic);
        return critic;


    }



}
