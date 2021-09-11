package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Provincia;
import ar.edu.unq.ttip.alec.backend.model.Responsable;
import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.service.ProvinciaService;
import ar.edu.unq.ttip.alec.backend.service.ResponsableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
public class AuxiliarController {

    @Autowired
    private ProvinciaService service;

    @Autowired
    private ResponsableService serviceResponsable;

    @GetMapping("/province")
    public ResponseEntity<List<Provincia>> getAllTax() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/responsable")
    public ResponseEntity<List<Responsable>> getAllResponsables() {
        return ResponseEntity.ok(serviceResponsable.findAll());
    }
}
