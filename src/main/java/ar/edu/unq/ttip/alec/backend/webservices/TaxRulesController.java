package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import ar.edu.unq.ttip.alec.backend.service.TaxRulesService;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxRulesDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
@RequestMapping("/broker/{brokerId}/taxRules")
@Api(value = "/broker/{brokerId}/taxRules", tags = "Tax Rules Controller", description = "Manage ALEC Taxes")
public class TaxRulesController {


    @Autowired
    private TaxRulesService service;

    @GetMapping
    @ApiOperation("Lists all Taxes.")
    public ResponseEntity<List<TaxRules>> getAllTaxRules() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @ApiOperation("Allow to add new Tax to existing Broker.")
    public ResponseEntity<TaxRulesDTO> createTaxRules(@PathVariable Integer brokerId, @RequestBody TaxRulesDTO request) {
        return new ResponseEntity(
                service.createTaxRules(brokerId, request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/add/{taxRuleId}")
    @ApiOperation("Allow to add existing Tax to existing Broker.")
    public ResponseEntity<TaxRulesDTO> addTaxRules(@PathVariable Integer brokerId, @PathVariable Integer taxRuleId) {
        return new ResponseEntity(
                service.addTaxRules(brokerId, taxRuleId),
                HttpStatus.CREATED
        );
    }




}
