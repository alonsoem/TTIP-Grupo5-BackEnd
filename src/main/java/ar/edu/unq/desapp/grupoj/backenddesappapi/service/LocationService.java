package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.LocationRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentLocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationService {

    @Autowired
    private LocationRepository repository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repository.save(new Location("Argentina","Buenos Aires"));
        repository.save(new Location("Argentina","Mar del Plata"));
        repository.save(new Location("Argentina","Rosario"));
        repository.save(new Location("Brasil","Rio de Janeiro"));
        repository.save(new Location("Chile","Valparaiso"));

    }

    public List<Location> findAll() {
        return repository.findAll();
    }

    public Location getById(Integer locationId) throws NonExistentLocationException {
        return repository.getById(locationId).orElseThrow(() -> new NonExistentLocationException(locationId));
    }
}
