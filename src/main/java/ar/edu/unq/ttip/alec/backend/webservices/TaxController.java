package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.service.TaxService;
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
@RequestMapping("/broker/{brokerId}/tax")
@Api(value = "/broker/{brokerId}/tax", tags = "Tax Controller", description = "Manage ALEC Taxes")
public class TaxController {


    @Autowired
    private TaxService service;

    @GetMapping
    @ApiOperation("Lists all Taxes.")
    public ResponseEntity<List<Tax>> getAllTaxes() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @ApiOperation("Allow to add new Tax to existing Broker.")
    public ResponseEntity<TaxDTO> createTax(@PathVariable Integer brokerId, @RequestBody TaxDTO request) {
        return new ResponseEntity(
                service.createTax(brokerId, request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/add/{taxId}")
    @ApiOperation("Allow to add existing Tax to existing Broker.")
    public ResponseEntity<TaxDTO> addTax(@PathVariable Integer brokerId, @PathVariable Integer taxId) {
        return new ResponseEntity(
                service.addTax(brokerId, taxId),
                HttpStatus.CREATED
        );
    }




}
