package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.MetricId;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Statistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstatisticsRepository extends CrudRepository<Statistics, MetricId> {
    List<Statistics> findAllByPlatformId(Integer platformId);
    Optional<Statistics> findByMethodNameAndPlatformId(String id, Integer platformId);



}


