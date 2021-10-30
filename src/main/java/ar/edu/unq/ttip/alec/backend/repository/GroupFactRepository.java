package ar.edu.unq.ttip.alec.backend.repository;

import ar.edu.unq.ttip.alec.backend.model.rules.GroupFact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupFactRepository extends CrudRepository<GroupFact, String> {

    List<GroupFact> findAll();


}



