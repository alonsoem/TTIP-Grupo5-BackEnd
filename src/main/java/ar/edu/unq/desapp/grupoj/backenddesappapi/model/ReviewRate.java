package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import jdk.jfr.Name;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Table (name = "review_rates",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","review_id"})}
)

public class ReviewRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Name("idReviewRate")
    private Integer id;

    private RateType type;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Review review;


    public ReviewRate(RateType type, User user, Review review){
        this.type=type;
        this.user=user;
        this.review=review;

    }
    protected ReviewRate(){}

    public RateType getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Review getReview() {
        return review;
    }
}
