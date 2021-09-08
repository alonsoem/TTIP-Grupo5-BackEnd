package ar.edu.unq.desapp.grupoj.backenddesappapi;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.RateType;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Rates;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Review;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Genre;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Title;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.ReviewService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.TitleService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.InverseReq;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.RateDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.ReviewDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.UserDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TitleServiceTests {

    @Autowired
    TitleService titleService;

    @Autowired
    ReviewService reviewService;

    @Test
    void retrieveAllTitlesAndGetNone(){
        List<Title> titles = titleService.findAll();
        assertEquals(4,titles.size());
    }

    @Test
    void retrieveAllTitlesAndGetOne() throws NonExistentTitleException {
        Title title = titleService.getByTitleId(1);
        assertEquals("PREDATOR",title.getTitle());
    }

    @Test
    void getInverseQuery() throws NonExistentReviewException, NonExistentSourceException, NonExistentLocationException, NonExistentTitleException, UserAlreadyReviewTitle, NonExistentLanguageException {
        InverseReq req = new InverseReq();
        req.actors=new ArrayList<>();
        req.actors.add("quique");
        req.decade=new ArrayList<>();
        req.decade.add("D2010");
        req.genres=new ArrayList<>();
        req.genres.add(Genre.COMEDY);
        req.minStars=2;
        req.rating= RateType.UP;


        UserDTO user = new UserDTO(1,"Jose","bla",1);
        ReviewDTO reviewDTO = new ReviewDTO(null,3,"text","text2",4,false,3,user);

        Review review =reviewService.save(reviewDTO);

        UserDTO userDto = new UserDTO(1,"Rogelio","pepe",1);
        RateDTO rate = new RateDTO(userDto,review.getId(),RateType.UP);

        Rates reviewRates = reviewService.rate(rate);

        List<Title> titles = titleService.inverseQuery(req);
        assertEquals(0,titles.size());
    }

}
