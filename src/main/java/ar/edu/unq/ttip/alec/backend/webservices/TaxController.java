package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Tax;
import ar.edu.unq.ttip.alec.backend.service.TaxService;
import ar.edu.unq.ttip.alec.backend.service.dtos.SwapRulesDto;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<TaxDTO>> getAllTaxes() {
        return ResponseEntity.ok(
                service.findAll()
                .stream()
                .map(tax->TaxDTO.fromModel(tax))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{taxId}")
    @ApiOperation("Get a Tax.")
    public ResponseEntity<TaxDTO> getTaxById(@PathVariable Integer taxId) {
        Tax t= service.getTaxById(taxId);
        return ResponseEntity.ok(
                TaxDTO.fromModel(t)
        );
    }


    @PutMapping("/{taxId}")
    @ApiOperation("Allow to update Tax.")
    public ResponseEntity<TaxDTO> update(@PathVariable Integer taxId, @RequestBody TaxDTO request) {
        return new ResponseEntity(
                service.update(taxId, request),
                HttpStatus.CREATED
        );
    }

    @PostMapping
    @ApiOperation("Allow to add new Tax to existing Broker.")
    public ResponseEntity<TaxDTO> createTax(@PathVariable Integer brokerId, @RequestBody TaxDTO request) {
        return new ResponseEntity(
                service.createTax(brokerId, request),
                HttpStatus.CREATED
        );
    }

    /*@PostMapping("/add/{taxId}")
    @ApiOperation("Allow to add existing Tax to existing Broker.")
    public ResponseEntity<TaxDTO> addTax(@PathVariable Integer brokerId, @PathVariable Integer taxId) {
        return new ResponseEntity(
                service.addTax(brokerId, taxId),
                HttpStatus.CREATED
        );
    }*/

    @DeleteMapping("/{taxId}")
    @ApiOperation("Allow to remove a Tax.")
    public HttpStatus remove(@PathVariable Integer taxId) {
        service.remove(taxId);
        return HttpStatus.OK;
    }



}
