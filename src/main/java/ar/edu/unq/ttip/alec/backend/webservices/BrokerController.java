package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.service.BrokerService;
import ar.edu.unq.ttip.alec.backend.service.dtos.BrokerDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalcResultDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.CalculationDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.SearchRequest;
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
@RequestMapping("/broker")
@Api(value="/broker",tags="Rate Broker Controller",description = "Manage ALEC Rate Brokers and calculations")
public class BrokerController {


    @Autowired
    private BrokerService service;

    @GetMapping
    @ApiOperation("List all Public Brokers")
    public ResponseEntity<List<BrokerDTO>> getAllPublicBrokers() {
        return ResponseEntity.ok(
                service.listAllPublicBrokers()
                        .stream()
                        .map(broker -> BrokerDTO.fromModel(broker))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/myBrokers")
    @ApiOperation("List Logged User Brokers")
    public ResponseEntity<List<BrokerDTO>> getLoggerUserBrokers() {
        return ResponseEntity.ok(
                service.filteredBrokers()
                        .stream()
                        .map(broker -> BrokerDTO.fromModel(broker))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/byUser/{userId}")
    @ApiOperation("List all User Brokers")
    public ResponseEntity<List<BrokerDTO>> getAllUserBrokers(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                service.getAllUserBrokers(userId)
                        .stream()
                        .map(broker -> BrokerDTO.fromModel(broker))
                        .collect(Collectors.toList())
        );
    }

    @PostMapping("/search")
    @ApiOperation("Get Brokers containing description")
    public ResponseEntity<List<BrokerDTO>> getBrokersByDescription(@RequestBody SearchRequest filter) {
        return ResponseEntity.ok(
                service.getBrokersByDescription(filter).stream().map(each->
                        BrokerDTO.fromModel(each)
                ).collect(Collectors.toList())
        );
    }

    @PostMapping("/search/{userId}")
    @ApiOperation("Get Brokers containing description")
    public ResponseEntity<List<BrokerDTO>> getBrokersByDescription(@RequestBody SearchRequest filter,
                                                                   @PathVariable Integer userId) {
        return ResponseEntity.ok(
                service.getUserBrokersByDescription(filter,userId).stream().map(each->
                        BrokerDTO.fromModel(each)
                ).collect(Collectors.toList())
        );
    }

    @GetMapping("/{brokerId}")
    @ApiOperation("Get One Broker")
    public ResponseEntity<BrokerDTO> getBrokerById(@PathVariable Integer brokerId) {
        return ResponseEntity.ok(
                BrokerDTO.fromModel(service.getBrokerById(brokerId))
        );
    }

    @PostMapping("/create")
    @ApiOperation("Allow to create a Broker")
    public ResponseEntity<BrokerDTO> createBroker(@RequestBody BrokerDTO request) {
        return new ResponseEntity(
                service.createBroker(request),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/copy/{brokerId}")
    @ApiOperation("Allow to copy a Broker from user to another")
    public ResponseEntity<BrokerDTO> copyBroker(@PathVariable Integer brokerId) {
        return new ResponseEntity(
                service.copyBroker(brokerId),
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

    @PutMapping("/{brokerId}")
    @ApiOperation("Allow to update a Broker")
    public ResponseEntity<BrokerDTO> update(@PathVariable Integer brokerId, @RequestBody BrokerDTO request) {
        return new ResponseEntity(
                service.update(brokerId,request),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{brokerId}")
    @ApiOperation("Allow to remove a Broker.")
    public HttpStatus remove(@PathVariable Integer brokerId) {
        service.remove(brokerId);
        return HttpStatus.OK;
    }

}
