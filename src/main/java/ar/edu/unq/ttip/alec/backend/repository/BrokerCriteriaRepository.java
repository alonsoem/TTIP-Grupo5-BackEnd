package ar.edu.unq.ttip.alec.backend.repository;


import ar.edu.unq.ttip.alec.backend.model.Broker;
import com.google.common.base.Predicates;
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

    public List<Broker> findAllPublicWithFilters(List<String> filter){
        CriteriaQuery<Broker> criteriaQuery = criteriaBuilder.createQuery(Broker.class);
        Root<Broker> brokerRoot = criteriaQuery.from(Broker.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add (this.baseFilterPredicates(filter, brokerRoot));
        predicates.add (this.isPublicPredicate(brokerRoot));

        return coreFilter(criteriaQuery, predicates);
    }

    public List<Broker> findAllPublicWithFilters(List<String> filter,Integer userId){
        CriteriaQuery<Broker> criteriaQuery = criteriaBuilder.createQuery(Broker.class);
        Root<Broker> brokerRoot = criteriaQuery.from(Broker.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add (this.baseFilterPredicates(filter, brokerRoot));
        predicates.add (this.isPublicPredicate(brokerRoot));
        predicates.add (this.isOwner(brokerRoot,userId));


        return coreFilter(criteriaQuery,predicates);
    }

    public List<Broker> findAllWithFilters(List<String> filter,Integer userId){
        CriteriaQuery<Broker> criteriaQuery = criteriaBuilder.createQuery(Broker.class);
        Root<Broker> brokerRoot = criteriaQuery.from(Broker.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add (baseFilterPredicates(filter, brokerRoot));
        predicates.add (isOwner(brokerRoot,userId));


        return coreFilter(criteriaQuery,predicates);
    }

    public List<Broker> coreFilter(CriteriaQuery<Broker> query, List<Predicate> predicates){

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        TypedQuery<Broker> consulta = entityManager.createQuery(query);
        List<Broker> results = consulta.getResultList();
        return results;
    }



    private Predicate baseFilterPredicates(List<String> filter, Root<Broker> brokerRoot) {
        List<Predicate> predicates = new ArrayList<>();

        filter.forEach(eachFilter->{
                if (Objects.nonNull(eachFilter)) {
                    predicates.add(criteriaBuilder.like(brokerRoot.get("name"), "%"+eachFilter+"%"));
                }}
        );

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate isPublicPredicate(Root<Broker> brokerRoot){
        return criteriaBuilder.isTrue(brokerRoot.get("isPublic"));
    }

    private Predicate isOwner(Root<Broker> brokerRoot,Integer userId){
        return criteriaBuilder.equal(brokerRoot.get("owner").get("id"),userId);
    }



}
