package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.service.BrokerService;
import ar.edu.unq.ttip.alec.backend.service.dtos.BrokerDTO;
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
@RequestMapping("/broker")
@Api(value="/broker",tags="Rate Broker Controller",description = "Manage ALEC Rate Brokers and calculations")
public class BrokerController {


    @Autowired
    private BrokerService service;

    @GetMapping
    @ApiOperation("List all Brokers")
    public ResponseEntity<List<Broker>> getAllBrokers() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{brokerId}")
    @ApiOperation("Get One Broker")
    public ResponseEntity<Broker> getBrokerById(@PathVariable Integer brokerId) {
        return ResponseEntity.ok(service.getBrokerById(brokerId));
    }

    @PostMapping("/create")
    @ApiOperation("Allow to create a Broker")
    public ResponseEntity<BrokerDTO> createBroker(@RequestBody BrokerDTO request) {
        return new ResponseEntity(
                service.createBroker(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/calculate")
    @ApiOperation("Allow to calculate a Rate aplication.")
    public ResponseEntity<CalcResultDTO> calculate(@RequestBody CalculationDTO request) {
        return new ResponseEntity(
                service.calculate(request.getAmount(),request.getApartado(),request.getTaxId()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/:id")
    @ApiOperation("Allow to update a Broker")
    public ResponseEntity<BrokerDTO> update(@PathVariable Integer id, @RequestBody BrokerDTO request) {
        return new ResponseEntity(
                service.update(id,request),
                HttpStatus.CREATED
        );
    }




}
