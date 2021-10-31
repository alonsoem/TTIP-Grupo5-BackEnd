package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.rules.Fact;
import ar.edu.unq.ttip.alec.backend.model.rules.GroupFact;
import ar.edu.unq.ttip.alec.backend.repository.GroupFactRepository;
import ar.edu.unq.ttip.alec.backend.service.exceptions.FactLoadFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class GroupFactService {

    @Autowired
    private GroupFactRepository repo;

    public List<GroupFact> findAll() {
        return repo.findAll();
    }

    public List<Fact> getFactsFromGroups() {
        return findAll().stream().flatMap((each)->{
            try {
                return each.getFacts().stream().filter(fact->
                        !fact.fixed());
            } catch (ClassNotFoundException e) {
                throw new FactLoadFailedException();
            }
        }
        ).collect(Collectors.toList());
    }
}