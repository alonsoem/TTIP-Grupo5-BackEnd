package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Language;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.LanguageRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentLanguageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class LanguageService {


    private LanguageRepository repository;

    @Autowired
    public LanguageService(LanguageRepository repo){
        repository=repo;
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repository.save(new Language("Español"));
        repository.save(new Language("Ingles"));
        repository.save(new Language("Portugues"));
        repository.save(new Language("Frances"));
    }

    public List<Language> findAll() {
        return repository.findAll();
    }

    public Language getById(Integer id) throws NonExistentLanguageException {

        return repository.getById(id).orElseThrow(() -> new NonExistentLanguageException(id));
    }

}
