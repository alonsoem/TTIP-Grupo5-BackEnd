package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Suscription;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.SuscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SuscriptionService {

    @Autowired
    private SuscriptionRepository repo;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save(new Suscription(1,"http://netflix.com/notification"));
        repo.save(new Suscription(1,"http://notification.disney.com"));
        repo.save(new Suscription(2,"http://notification.netflix.com"));
        repo.save(new Suscription(1,"http://notify.paramount.com"));
    }

    public List<Suscription> getAllByTitleId(Integer titleId){
        return repo.getAllByTitleId(titleId);
    }

    public Suscription getByUrl(String url) {
        return repo.getByUrl(url);
    }

    @Transactional
    public Suscription save(Suscription suscription) {
        repo.save(suscription);
        return suscription;
    }


}
