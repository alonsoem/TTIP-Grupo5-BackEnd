package ar.edu.unq.desapp.grupoj.backenddesappapi.webservices;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Statistics;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration
public class EstatisticsController {

    @Autowired
    private StatisticsService service;


    @GetMapping("/statistics")
    @Description("All Metrics consumed")
    public ResponseEntity<List<Statistics>> getAllStatistics() {
        return ResponseEntity.ok(service.getAllStatisticsByPlatform());

    }

}
