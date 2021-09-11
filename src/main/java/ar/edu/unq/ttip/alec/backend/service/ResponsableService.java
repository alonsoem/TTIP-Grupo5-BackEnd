package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.Provincia;
import ar.edu.unq.ttip.alec.backend.model.Responsable;
import ar.edu.unq.ttip.alec.backend.repository.ResponsablesAFIPRepository;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentResponsableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResponsableService {


    @Autowired
    private ResponsablesAFIPRepository repo;



    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        repo.save (new Responsable("IVA Responsable Inscripto"));
        repo.save (new Responsable("IVA Responsable No Inscripto"));
        repo.save (new Responsable("IVA No Responsable"));
        repo.save (new Responsable("IVA Sujeto Exento"));
        repo.save (new Responsable("Consumidor Final"));
        repo.save (new Responsable("Responsable Monotributo"));
        repo.save (new Responsable("Sujeto No Categorizado"));
        repo.save (new Responsable("Proveedor del exterior"));
        repo.save (new Responsable("IVA Liberado – Ley Nº 19.640"));
        repo.save (new Responsable("IVA Responsable Inscripto – Agente de Percepción"));
        repo.save (new Responsable("Pequeño Contribuyente Eventual"));
        repo.save (new Responsable("Monotributista Social"));
        repo.save (new Responsable("Pequeño Contribuyente Eventual Social"));


    }

    public Responsable getByResponsableId(Integer id) {
        return repo.getResponsableById(id).orElseThrow(() -> new NonExistentResponsableException(id));
    }

    public List<Responsable> findAll() {
        return repo.findAll();
    }

    public Responsable save(Responsable responsable){
        return repo.save(responsable);
    }



}