package ar.edu.unq.desapp.grupoj.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Tax;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.TaxService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NoExistentTaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<Tax> getTitleById(@PathVariable(value = "id") Integer id) throws NoExistentTaxException {
        return ResponseEntity
                .ok(
                    service.getByTitleId(id)
                    );
    }




}


