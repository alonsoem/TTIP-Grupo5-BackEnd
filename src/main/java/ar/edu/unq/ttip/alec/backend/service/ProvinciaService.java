package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.Provincia;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.repository.ProvinciaRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.FrontUserDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.RegisterDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentProvinciaException;
import ar.edu.unq.ttip.alec.backend.service.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class ProvinciaService {


    @Autowired
    private ProvinciaRepository repo;



    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save (new Provincia("C.A.B.A."));
        repo.save (new Provincia("Buenos Aires"));
        repo.save (new Provincia("Santa Fé"));
        repo.save (new Provincia("Córdoba"));
        repo.save (new Provincia("Mendoza"));
        repo.save (new Provincia("Jujuy"));

    }

    public Provincia getByProvinciaId(Integer id) {
        return repo.getProvinciaById(id).orElseThrow(() -> new NonExistentProvinciaException(id));
    }

    public List<Provincia> findAll() {
        return repo.findAll();
    }


    public Provincia save(Provincia provincia){
        return repo.save(provincia);
    }




}