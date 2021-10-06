package ar.edu.unq.ttip.alec.backend.repository;

import ar.edu.unq.ttip.alec.backend.model.rules.TaxRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxRulesRepository extends JpaRepository<TaxRules, Integer> {

    List<TaxRules> findAll();
    Optional<TaxRules> getTaxRulesById(Integer id);

}



