package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.rules.GroupFact;
import ar.edu.unq.ttip.alec.backend.repository.GroupFactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupFactService {

    @Autowired
    private GroupFactRepository repo;

    public List<GroupFact> findAll() {
        return repo.findAll();
    }

}