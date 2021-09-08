package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Review;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.ReviewPage;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.ReviewSearchCriteria;
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
public class ReviewCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ReviewCriteriaRepository(EntityManager entityManager){
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Review> findAllWithFilters(ReviewPage reviewPage, ReviewSearchCriteria reviewSearchCriteria){

        CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
        Root<Review> reviewRoot = criteriaQuery.from(Review.class);
        Predicate predicate = getPredicate(reviewSearchCriteria, reviewRoot);
        criteriaQuery.where(predicate);
        //ahora ordeno los resultados
        setOrder(reviewPage, criteriaQuery, reviewRoot);


        TypedQuery<Review> typedQuery  = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(reviewPage.getPageNumber() * reviewPage.getPageSize());
        typedQuery.setMaxResults(reviewPage.getPageSize());

        Pageable pageable = getPageable(reviewPage);

        //ahora va a crear la countQuery
        long reviewsCount = getReviewsCount(predicate);
        //quiero obtener el total de los reviews

        return new PageImpl<>(typedQuery.getResultList(), pageable, reviewsCount);
    }

    private Predicate getPredicate(ReviewSearchCriteria reviewSearchCriteria, Root<Review> reviewRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(reviewSearchCriteria.isSpoilerAlert())) {
            predicates.add(
                    criteriaBuilder.equal(reviewRoot.get("spoilerAlert"), reviewSearchCriteria.isSpoilerAlert()
                            )
            );
        }

        if (Objects.nonNull(reviewSearchCriteria.getSource())) {
            predicates.add(
                    criteriaBuilder.equal(reviewRoot.get("user").get("source").get("id"),  reviewSearchCriteria.getSource()
                    )
            );
        }

        if (Objects.nonNull(reviewSearchCriteria.getLanguage())) {
            predicates.add(
                    criteriaBuilder.equal(reviewRoot.get("language").get("id"),  reviewSearchCriteria.getLanguage()
                    )
            );
        }

        if (Objects.nonNull(reviewSearchCriteria.getReviewType())) {
            predicates.add(
                    criteriaBuilder.equal(reviewRoot.get("type"),  reviewSearchCriteria.getReviewType()
                    )
            );
        }

        if (Objects.nonNull(reviewSearchCriteria.getLocationCountry())) {
            predicates.add(
                    criteriaBuilder.equal(reviewRoot.get("user").get("location").get("country"),  reviewSearchCriteria.getLocationCountry()
                    )
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    //que orden si el reviewPage.getSortDirection es asc o desc ordena de diferente manera

    private void setOrder(ReviewPage reviewPage, CriteriaQuery<Review> criteriaQuery, Root<Review> reviewRoot) {
        if(reviewPage.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(reviewRoot.get(reviewPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(reviewRoot.get(reviewPage.getSortBy())));
        }
    }
    private Pageable getPageable(ReviewPage reviewPage) {
        Sort sort = Sort.by(reviewPage.getSortDirection(), reviewPage.getSortBy());
        return PageRequest.of(reviewPage.getPageNumber(), reviewPage.getPageSize(), sort);
    }


    private long getReviewsCount(Predicate predicate) {
        CriteriaQuery <Long> countQuery  = criteriaBuilder.createQuery(Long.class);
        Root<Review> countRoot = countQuery.from(Review.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();


    }

}
