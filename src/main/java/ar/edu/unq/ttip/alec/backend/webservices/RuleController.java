package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.service.RuleService;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
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
@RequestMapping("/tax/{taxId}/rule")
@Api(value = "/tax/{taxId}/rule", tags = "Rule Controller",description = "Manage ALEC Rules to use in calculations.")
public class RuleController {


    @Autowired
    private RuleService service;

    @GetMapping()
    @ApiOperation("Get all rules")
    public ResponseEntity<List<Rule>> getAllRules() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @ApiOperation("Allow to add new Rule to existing Rate")
    public ResponseEntity<RuleDTO> createRule(@PathVariable Integer taxId, @RequestBody RuleDTO request) {
        return new ResponseEntity(
                service.create(taxId, request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/add/{ruleId}")
    @ApiOperation("Allow to add existing Rule to existing Rate")
    public ResponseEntity<RuleDTO> add(@PathVariable Integer taxId, @PathVariable Integer ruleId) {
        return new ResponseEntity(
                service.add(taxId, ruleId),
                HttpStatus.CREATED
        );
    }




}
