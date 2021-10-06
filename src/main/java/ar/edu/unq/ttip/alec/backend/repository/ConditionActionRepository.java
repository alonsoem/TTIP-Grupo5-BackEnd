package ar.edu.unq.ttip.alec.backend.repository;


import ar.edu.unq.ttip.alec.backend.model.rules.ConditionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConditionActionRepository extends JpaRepository<ConditionAction, Integer> {

    List<ConditionAction> findAll();
    Optional<ConditionAction> getById(Integer id);
}



