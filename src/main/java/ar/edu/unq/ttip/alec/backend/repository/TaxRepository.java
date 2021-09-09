package ar.edu.unq.ttip.alec.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.ttip.alec.backend.model.Tax;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRepository extends CrudRepository<Tax, Integer> {

    List<Tax> findAll();
    Optional<Tax> getTaxById(Integer id);

}



