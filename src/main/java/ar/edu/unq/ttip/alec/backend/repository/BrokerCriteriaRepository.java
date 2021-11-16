package ar.edu.unq.ttip.alec.backend.repository;


import ar.edu.unq.ttip.alec.backend.model.Broker;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BrokerCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public BrokerCriteriaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public List<Broker> findAllWithFilters(List<String> filter){

        CriteriaQuery<Broker> criteriaQuery = criteriaBuilder.createQuery(Broker.class);
        Root<Broker> brokerRoot = criteriaQuery.from(Broker.class);
        Predicate predicate = getPredicate(filter, brokerRoot);
        criteriaQuery.where(predicate);

        TypedQuery<Broker> consulta = entityManager.createQuery(criteriaQuery);

        List<Broker> results = consulta.getResultList();
        return results;
    }

    private Predicate getPredicate(List<String> filter, Root<Broker> brokerRoot) {
        List<Predicate> predicates = new ArrayList<>();

        filter.forEach(eachFilter->{
                if (Objects.nonNull(eachFilter)) {
                    predicates.add(criteriaBuilder.like(brokerRoot.get("name"), "%"+eachFilter+"%"));
                }}
        );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }



}
