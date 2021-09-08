package ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Language;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Cast;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Job;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Person;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Review;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import org.yaml.snakeyaml.util.ArrayUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="titles")

public class Title implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer titleId;
    private TitleType titleType;
    private String title;
    private Boolean isAdult;
    private Integer startYear;
    private Integer endYear;
    private Integer duration;
    private Integer reviewCount;
    private Double averageRating;
    private static final long serialVersionUID = 0L;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Genre.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "generos_enum")
    @Column(name = "generos")
    private List<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Cast> cast= new ArrayList<Cast>();


    public Title(Integer id, TitleType type, String title, Boolean isAdult, Integer startYear, Integer endYear, Integer duration, List<Genre> genres, Integer reviewCount, Double averageRating){
        this.titleId=id;
        this.titleType=type;
        this.title=title;
        this.isAdult=isAdult;
        this.startYear=startYear;
        this.endYear=endYear;
        this.duration=duration;
        this.genres=genres;
        this.reviewCount=reviewCount;
        this.averageRating=averageRating;
    }



    public Title(){}

    public void addReview(Review review){
        reviews.add(review);
    }

    public void addCast(Person person, Job job){
        cast.add(new Cast(person,job));
    }

    public Integer getTitleId() {
        return titleId;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public String getTitle() {
        return title;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setReviewCount(Integer count) {
        this.reviewCount = count;
    }

    public Integer getReviewCount(){
        return this.reviewCount;
    }

    public List<Review> getReviews(){
        return reviews;
    }

    public Double getAverageRating() {
        Double rating = 0.0;
        if (  reviews.size() == 0 ) {
            averageRating = 0.00;
        } else {
            for (Review review:reviews) {
                rating += review.getRating();
            }
            averageRating = rating/this.getReviewCount();
        }
        return averageRating;
    }


    public void setAverageRating(double averageRating) {
    }
}