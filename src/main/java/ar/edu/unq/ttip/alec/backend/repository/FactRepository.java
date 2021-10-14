package ar.edu.unq.ttip.alec.backend.repository;

import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactRepository extends CrudRepository<Fact, String> {

    List<Fact> findAll();
    Optional<Fact> getFactByName(String name);

}



