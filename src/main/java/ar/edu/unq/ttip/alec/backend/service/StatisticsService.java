package ar.edu.unq.ttip.alec.backend.service;


import ar.edu.unq.ttip.alec.backend.model.Statistics;
import ar.edu.unq.ttip.alec.backend.repository.EstatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class StatisticsService {

    @Autowired
    private EstatisticsRepository repo;

    @Transactional
    public void update(Integer brokerId){
        Statistics methodData = repo
                .findByBrokerId(brokerId)
                .orElse(new Statistics(brokerId));

        methodData.update();
        repo.save(methodData);
    }
}
