package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Decade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DecadeRepository extends CrudRepository<Decade, String> {
    List<Decade> findAll();
    Optional<Decade> getById(String id);
    List<Decade> getAllByIdIn(List<String> ids);

}


