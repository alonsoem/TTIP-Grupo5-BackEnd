package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.service.TaxRulesService;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxRulesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
@RequestMapping("/broker/{brokerId}/taxRules")
public class TaxRulesController {


    @Autowired
    private TaxRulesService service;

    @GetMapping
    public ResponseEntity<List<TaxRules>> getAllTaxRules() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<TaxRulesDTO> createTaxRules(@PathVariable Integer brokerId, @RequestBody TaxRulesDTO request) {
        return new ResponseEntity(
                service.createTaxRules(brokerId, request),
                HttpStatus.CREATED
        );
    }




}
