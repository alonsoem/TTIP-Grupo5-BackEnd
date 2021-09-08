package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriticRepository extends CrudRepository<Critic, Integer> {
    Iterable<Critic> findAll();
    Optional<Critic> findBySourceAndUserId(Source sourceId, String userId);

}


