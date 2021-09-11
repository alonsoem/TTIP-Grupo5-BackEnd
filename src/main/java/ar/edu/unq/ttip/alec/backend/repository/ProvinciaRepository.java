package ar.edu.unq.ttip.alec.backend.repository;

import ar.edu.unq.ttip.alec.backend.model.Provincia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends CrudRepository<Provincia, Integer> {

    List<Provincia> findAll();
    Optional<Provincia> getProvinciaById(Integer id);

}



