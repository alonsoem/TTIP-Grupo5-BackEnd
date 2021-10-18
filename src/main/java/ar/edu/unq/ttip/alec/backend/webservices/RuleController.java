package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.service.RuleService;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
@RequestMapping()
@Api(value = "/tax/{taxId}/rule", tags = "Rule Controller",description = "Manage ALEC Rules to use in calculations.")
public class RuleController {


    @Autowired
    private RuleService service;

    @GetMapping("/rule")
    @ApiOperation("Get all rules")
    public ResponseEntity<List<Rule>> getAllRules() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/rule/{ruleId}")
    @ApiOperation("Get One Rule")
    public ResponseEntity<Rule> getRuleById(@PathVariable Integer ruleId) {
        return ResponseEntity.ok(service.getRuleById(ruleId));
    }

    @GetMapping("/facts")
    @ApiOperation("Get all facts")
    public ResponseEntity<List<Fact>> getAllFacts() {
        return ResponseEntity.ok(service.findAllFacts());
    }

    @PostMapping("/tax/{taxId}/rule")
    @ApiOperation("Allow to add new Rule to existing Tax")
    public ResponseEntity<RuleDTO> createRule(@PathVariable Integer taxId, @RequestBody RuleDTO request) {
        return new ResponseEntity(
                service.create(taxId, request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/tax/{taxId}/rule/add/{ruleId}")
    @ApiOperation("Allow to add existing Rule to existing Rate")
    public ResponseEntity<RuleDTO> add(@PathVariable Integer taxId, @PathVariable Integer ruleId) {
        return new ResponseEntity(
                service.add(taxId, ruleId),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/tax/{taxId}/rule/{ruleId}")
    @ApiOperation("Allow to update Tax.")
    public ResponseEntity<TaxDTO> update(@PathVariable Integer ruleId, @RequestBody RuleDTO request) {
        return new ResponseEntity(
                service.update(ruleId, request),
                HttpStatus.CREATED
        );
    }




}
