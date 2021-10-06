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
@RequestMapping("/taxRule/{taxRuleId}/rule")
@Api(value = "/taxRule/{taxRuleId}/rule", tags = "Rule Controller",description = "Manage ALEC Rules to use in calculations.")
public class RuleController {


    @Autowired
    private RuleService service;

    @GetMapping()
    @ApiOperation("Get all rules")
    public ResponseEntity<List<Rule>> getAllRules() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @ApiOperation("Allow to add new Rule to existing Tax")
    public ResponseEntity<RuleDTO> createRule(@PathVariable Integer taxRuleId, @RequestBody RuleDTO request) {
        return new ResponseEntity(
                service.create(taxRuleId, request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/add/{ruleId}")
    @ApiOperation("Allow to add existing Rule to existing Tax")
    public ResponseEntity<RuleDTO> add(@PathVariable Integer taxRuleId, @PathVariable Integer ruleId) {
        return new ResponseEntity(
                service.add(taxRuleId, ruleId),
                HttpStatus.CREATED
        );
    }




}
