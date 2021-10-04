package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.rules.Rule;
import ar.edu.unq.ttip.alec.backend.service.RuleService;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
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
@RequestMapping("/taxRule/{taxRuleId}/rule")
public class RuleController {


    @Autowired
    private RuleService service;

    @GetMapping()
    @Description("Get all rules")
    public ResponseEntity<List<Rule>> getAllRules() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Description("Allow to add new Rule to existing Tax")
    public ResponseEntity<RuleDTO> createRule(@PathVariable Integer taxRuleId, @RequestBody RuleDTO request) {
        return new ResponseEntity(
                service.create(taxRuleId, request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/add/{ruleId}")
    @Description("Allow to add existing Rule to existing Tax")
    public ResponseEntity<RuleDTO> add(@PathVariable Integer taxRuleId, @PathVariable Integer ruleId) {
        return new ResponseEntity(
                service.add(taxRuleId, ruleId),
                HttpStatus.CREATED
        );
    }




}
