package ar.edu.unq.desapp.grupoj.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoj.backenddesappapi.service.LocationService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.LocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
public class LocationController {

    @Autowired
    private LocationService service;

    @GetMapping("/location")
    @Description("All Locations list")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return new ResponseEntity<List<LocationDTO>>(
                service.findAll()
                        .stream()
                        .map(i->LocationDTO.fromModel(i))
                        .collect(Collectors.toList())
                , HttpStatus.OK
        );
    }




}
