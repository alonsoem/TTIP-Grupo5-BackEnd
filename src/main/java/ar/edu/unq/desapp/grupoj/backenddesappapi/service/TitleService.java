package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.ReviewDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentTitleException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Job;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Person;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Decade;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Review;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.*;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.DecadeRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.titlesRepository.TitleRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.titlesRepository.TitleRepositoryQueries;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.InverseReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class    TitleService {


    @Autowired
    private DecadeRepository decadeRepo;


    @Autowired
    private TitleRepositoryQueries titleRepoQ;

    @Autowired
    private TitleRepository titleRepo;

    @Autowired
    private ReviewService reviewService;


    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        ArrayList<Genre> genres= new ArrayList<>();
        genres.add(Genre.ACTION);
        genres.add(Genre.DRAMA);
        titleRepo.save(new Title(1,TitleType.MOVIE,"PREDATOR",false,2010,2010,100,genres,0,0.00));


        ArrayList<Genre> genresb= new ArrayList<>();
        genresb.add(Genre.ACTION);

        titleRepo.save(new Title(2,TitleType.MOVIE,"TERMINATOR 10",false,2020,2021,100,genresb,0,0.00));

        ArrayList<Genre> genres2= new ArrayList<>();
        genres2.add(Genre.ACTION);

        titleRepo.save(new Title(3,TitleType.TVSERIES,"LOST",false,2010,2011,40,genres2,0,0.00));

        ArrayList<Genre> genres3= new ArrayList<>();
        genres3.add(Genre.COMEDY);
        Title title3= new Title(21,TitleType.TVEPISODE,"LOST: Chapter 1 'Pilot'",false,2014,2015,40,genres3,0,0.00);
        title3.addCast(new Person("Jose"), Job.DIRECTOR);
        title3.addCast(new Person("Pedro"), Job.ACTOR);

        titleRepo.save(title3);

    }

    public Title getByTitleId(Integer id) throws NonExistentTitleException {
        return this.titleRepo.getByTitleId(id).orElseThrow(() -> new NonExistentTitleException(id));
    }

    public List<Title> findAll() {
        return this.titleRepo.findAll();
    }

    void addReviewToTitle(Review review, Integer titleId) throws NonExistentTitleException{
        Title title = getByTitleId(titleId);
        title.addReview(review);
        titleRepo.save(title);
    }

    public List<Title> inverseQuery(InverseReq req) {
        List<Decade> decades= decadeRepo.getAllByIdIn(req.decade);
        return titleRepoQ.inverseQuery(req,decades);
    }

    @CacheEvict(value = "titleInfo",allEntries = true)
    public Title getTitleInfo(Integer id) throws NonExistentTitleException {
        Optional<Title> optionalTitle = titleRepo.findById(id);
        if (optionalTitle.isPresent()){
            Title title = optionalTitle.get();
            List <Review> reviews = reviewService.findAllByIdTitle(title.getTitleId());
            title.setReviewCount(reviews.size());
            return title;
        }
        throw new NonExistentTitleException(id);
    }



}