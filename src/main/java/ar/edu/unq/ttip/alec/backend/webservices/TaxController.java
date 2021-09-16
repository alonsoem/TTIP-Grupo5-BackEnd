package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.service.TaxService;
import ar.edu.unq.ttip.alec.backend.service.exceptions.NonExistentTaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
public class TaxController {

    @Autowired
    private TaxService service;

    @GetMapping("/tax")
    public ResponseEntity<List<Tax>> getAllTax() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/tax/{id}")
    public ResponseEntity<Tax> getTitleById(@PathVariable(value = "id") Integer id) throws NonExistentTaxException {
        return ResponseEntity
                .ok(
                    service.getByTitleId(id)
                    );
    }




}


