package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.ReviewDTO;
import jdk.jfr.Name;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "reviews",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"titleId","user_id"})}
)
public class Review  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer titleId;

    private String text;
    private String textExtended;
    private Integer rating;
    private boolean spoilerAlert=false;
    private Date date=Date.from(Instant.now());
    private Long ratedUp=(long) 0;
    private Long ratedDown= (long) 0;

    @OneToOne
    protected Critic user;

    @ManyToOne(cascade = CascadeType.ALL)
    protected Language language;


    protected ReviewType type=ReviewType.NORMAL;

    @OneToMany(targetEntity= ReviewRate.class, cascade = CascadeType.ALL)
    @Name("reviewRates")
    private List<ReviewRate> reviewRates = new ArrayList<>();

    @OneToMany(targetEntity= ReviewReport.class, cascade = CascadeType.ALL)
    @Name("reviewReports")
    private List<ReviewReport> reviewReports= new ArrayList<>();

    protected Review() {
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public Review(Integer titleId, Critic criticOrUser, String text, String textExtended, Integer rating, Boolean haveSpoiler, Language language){
        this.user=criticOrUser;
        this.text=text;
        this.rating = rating;
        this.textExtended= textExtended;
        this.titleId=titleId;
        this.spoilerAlert=haveSpoiler;
        this.language=language;

    }

    public Integer getRating() {
        return rating;
    }

    public Critic getUser() {
        return user;
    }

    public String getText() {
        return text;
    }
    public String getTextExtended() {return textExtended;}
    public Integer getTitleId() {
        return titleId;
    }

    public boolean getSpoilerAlert() {
        return spoilerAlert;
    }

    public Date getDate() {
        return date;
    }


    public Integer getId() {
        return id;
    }


    public ReviewType getType() {
        return type;
    }

    public Rates getReviewRate() {
        Rates rate = new Rates();
        rate.ratingUp= this.ratedUp;
        rate.ratingDown=this.ratedDown;
        return rate;
    }


    public Long getReviewRateInt(){
        return this.ratedUp-this.ratedDown;
    }

    public Language getLanguage() {
        return language;
    }

    public void addRate(ReviewRate reviewRate){
        reviewRates.add(reviewRate);
        calculateRates();

    }

    public void addReport(ReviewReport reviewReport) {
        reviewReports.add(reviewReport);

    }

    private void calculateRates(){
        this.ratedUp=  reviewRates.stream().filter(i -> i.getType() == RateType.UP).count();
        this.ratedDown= reviewRates.stream().filter(i -> i.getType()==RateType.DOWN).count();
    }

    public void setDate(Date date) {
        this.date = date;
    }
/*
    public ReviewDTO toDTO(Integer titleId, User user, String text, String textExtended, Integer rating, boolean spoilerAlert,Language language) {
        return new ReviewDTO(titleId, text, textExtended, rating, spoilerAlert,language);
    }
*/
}
