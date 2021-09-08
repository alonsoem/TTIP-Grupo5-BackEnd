package ar.edu.unq.desapp.grupoj.backenddesappapi.repository.titlesRepository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Decade;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Review;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Cast;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Person;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Genre;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Title;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.InverseReq;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class TitleRepositoryQueries {


    @PersistenceContext
    EntityManager em;


    public List<Title> inverseQuery(InverseReq req, List<Decade> decades)
    {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Title> cq = cb.createQuery(Title.class);


        Root<Title> title = cq.from(Title.class);
        Join<Title,Review> joinTitleReviews= title.join("reviews");




        Expression<List<Genre>> genreProperty = title.get("genres");
        Predicate genrePredicate = cb.conjunction();
        for(Genre oneGenre: req.genres){
            genrePredicate = cb.and(genrePredicate, cb.isMember(oneGenre, genreProperty));
        }


        Expression<Integer> startYear= title.get("startYear");
        Expression<Integer> endYear= title.get("endYear");
        Predicate yearPredicate= cb.disjunction();


        for(Decade oneDecade: decades){
            yearPredicate= cb.or(yearPredicate, cb.and(cb.greaterThanOrEqualTo(startYear,oneDecade.getFrom()),
                                                    cb.lessThanOrEqualTo(startYear,oneDecade.getTo())));

            yearPredicate= cb.or(yearPredicate, cb.and(cb.greaterThanOrEqualTo(endYear,oneDecade.getFrom()),
                                                    cb.lessThanOrEqualTo(endYear,oneDecade.getTo())));

        }


        //SubQuery para filtrar los titulos que tienen a los actores que se solicitan

            Subquery<Long> subqueryActors = cq.subquery(Long.class);
            Root<Title> subRootTitle = subqueryActors.from(Title.class);
            Join<Title, Cast> joinCast = subRootTitle.join("cast");
            Join<Cast, Person> joinPerson = joinCast.join("person");

            Path<Long> idPathToTitle = subRootTitle.get("titleId");
            Expression<String> nameProp = joinPerson.get("name");

            Predicate predicateD = cb.disjunction();
            for (String oneName : req.actors) {
                predicateD = cb.or(predicateD, cb.like(nameProp, oneName));
            }

            Expression<Long> idCount = cb.count(joinPerson.get("name"));
            subqueryActors.select(idPathToTitle)
                    .where(predicateD)
                    .groupBy(idPathToTitle)
                    .having(cb.gt(idCount, 0))
                    .distinct(true);



        //SubQuery para filtrar los titulos tiene reviews con menos estrellas de las deseadas
        Subquery<Long> subQueryEstrellas = cq.subquery(Long.class);
        Root<Title> subRoot = subQueryEstrellas.from(Title.class);
        Join<Review, Title> join = subRoot.join("reviews");
        Path<Long> idPath = join.get("titleId");
        Expression<Long> idCountExp = cb.count(join.get("id"));
        subQueryEstrellas.select(idPath)
                .where(cb.lt(join.get("rating"),req.minStars))
                .groupBy(idPath)
                .having(cb.gt(idCountExp, 0))
                .distinct(true);


        //Subquery para filtrar los calificados positivamente
        Subquery<Long> subQueryRatedUp = cq.subquery(Long.class);
        Root<Title> subRoot2 = subQueryRatedUp.from(Title.class);
        Join<Review, Title> join2 = subRoot2.join("reviews");
        Path<Long> idPath2 = join2.get("titleId");
        Expression<Long> rateDiff = cb.diff(join2.get("ratedUp"),join2.get("ratedDown"));
        Predicate predicate = cb.lessThan(rateDiff,(long) 0);
        subQueryRatedUp.select(idPath2)
                .where(predicate)
                .groupBy(idPath2)
                .distinct(true);

        Predicate finalConjunction = cb.conjunction();


        finalConjunction= cb.and(finalConjunction,cb.not(title.get("titleId").in(subQueryEstrellas)));
        finalConjunction= cb.and(finalConjunction,cb.not(title.get("titleId").in(subQueryRatedUp)));

        if(!req.decade.isEmpty()) {
            finalConjunction = cb.and(finalConjunction, yearPredicate);
        }

        if (!req.genres.isEmpty()) {
            finalConjunction = cb.and(finalConjunction, genrePredicate);
        }
        if(!req.actors.isEmpty()) {
            finalConjunction = cb.and(finalConjunction, title.get("titleId").in(subqueryActors));
        }

        cq.where(finalConjunction );



        TypedQuery<Title> query = em.createQuery(cq);
        return query.getResultList();

    }


}


