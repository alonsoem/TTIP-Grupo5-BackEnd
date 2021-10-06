package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.tax.Tax;
import ar.edu.unq.ttip.alec.backend.service.TaxService;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalculationDTO;
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
@RequestMapping("/tax")
@Api(value = "/tax", tags = "Tax Controller (OLD VERSION)",description = "Manage ALEC Taxes (OLD VERSION)")
public class TaxController {

    @Autowired
    private TaxService service;

    @GetMapping()
    @ApiOperation("Get All taxes (old version)")
    public ResponseEntity<List<Tax>> getAllTax() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/calculate")
    @ApiOperation("Allow to calculate Tax aplication")
    public ResponseEntity<CalcResultDTO> taxCalculate(@RequestBody CalculationDTO request) {
        return new ResponseEntity(
                service.calculate(request.getAmount(),request.getApartado(),request.getTaxId()),
                HttpStatus.CREATED
        );
    }

}


