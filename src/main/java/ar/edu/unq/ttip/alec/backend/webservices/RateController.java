package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Rate;
import ar.edu.unq.ttip.alec.backend.service.RateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
@RequestMapping("/rate")
@Api(value = "/rate", tags = "Rate Controller",description = "Manage ALEC Rates")
public class RateController {

    @Autowired
    private RateService service;

    @GetMapping()
    @ApiOperation("Get All rates")
    public ResponseEntity<List<Rate>> getAllRates() {
        return ResponseEntity.ok(service.findAll());
    }


}


