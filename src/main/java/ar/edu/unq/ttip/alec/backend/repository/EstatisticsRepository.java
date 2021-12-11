package ar.edu.unq.ttip.alec.backend.repository;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import ar.edu.unq.ttip.alec.backend.model.MetricId;
import ar.edu.unq.ttip.alec.backend.model.Statistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstatisticsRepository extends CrudRepository<Statistics, MetricId> {

    Optional<Statistics> findByBrokerId(Integer brokerId);





}


