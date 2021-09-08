package ar.edu.unq.desapp.grupoj.backenddesappapi;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Review;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.ReviewType;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Reason;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Language;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Rates;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.RateType;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.ReviewReport;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.ReviewService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.ReviewPremiumDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.ReviewDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.ReportDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.RateDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.UserDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.CriticDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.*;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ReviewServiceTests {

    @Autowired
    ReviewService reviewService;

    @Test
    void  retrieveAllReviewsAndGetNone () throws NonExistentTitleException {
        List<Review> reviews = reviewService.findAllByIdTitle(1);
        assertEquals(2,reviews.size());
    }
    @Test
    void  addOneReviewAndThenGetAllReviews () throws NonExistentSourceException, UserAlreadyReviewTitle, NonExistentLanguageException, NonExistentLocationException, NonExistentTitleException {
        UserDTO user = new UserDTO(3,"DummyUser","bla",1);
        ReviewDTO reviewDTO = new ReviewDTO(null,1,"","",4,false,1,user);

        reviewService.save(reviewDTO);
        List<Review> reviews = reviewService.findAllByIdTitle(1);
        assertEquals(4,reviews.size());
    }

    @Test
    void  addOnePremiumReviewAndThenGetAllReviews () throws NonExistentSourceException, UserAlreadyReviewTitle, NonExistentLanguageException, NonExistentTitleException, NonExistentLocationException, NonExistentCriticException {
         ReviewPremiumDTO reviewDTO = new ReviewPremiumDTO();
        reviewDTO.languageId=1;
        reviewDTO.rating=4;
        reviewDTO.spoilerAlert=false;
        reviewDTO.titleId=1;
        reviewDTO.type= ReviewType.NORMAL;
        reviewDTO.critic=new CriticDTO(2,"bla",1);


        reviewService.savePremium(reviewDTO);
        List<Review> reviews = reviewService.findAllByIdTitle(1);
        assertEquals(5,reviews.size());
    }

    @Test
    void  getAllReviewsNotJustTitleNumber1 () throws NonExistentSourceException, UserAlreadyReviewTitle, NonExistentLanguageException, NonExistentTitleException, NonExistentLocationException, NonExistentCriticException {
        Language language = Mockito.mock(Language.class);
        User user = Mockito.mock(User.class);

        ReviewPremiumDTO reviewDTO = new ReviewPremiumDTO();
        reviewDTO.languageId=1;
        reviewDTO.rating=4;
        reviewDTO.spoilerAlert=false;
        reviewDTO.titleId=2;
        reviewDTO.type= ReviewType.NORMAL;
        reviewDTO.critic=new CriticDTO(3,"bla",2);


        reviewService.savePremium(reviewDTO);
        List<Review> reviews = reviewService.findAll();
        assertEquals(5,reviews.size());
    }

    @Test
    void  rateReviewAndGetEstatistics () throws NonExistentSourceException, NonExistentLocationException, NonExistentReviewException, NonExistentTitleException, UserAlreadyReviewTitle, NonExistentLanguageException {
        UserDTO user = new UserDTO(1,"bla","bla",1);
        ReviewDTO reviewDTO = new ReviewDTO(null,1,"","",4,false,1,user);

        Review review =reviewService.save(reviewDTO);

        UserDTO userDto = new UserDTO(1,"quique","pepe",1);
        RateDTO rate = new RateDTO(userDto,review.getId(),RateType.UP);

        Rates reviewRates = reviewService.rate(rate);


        assertEquals(1,reviewRates.getRatingUp());
        assertEquals(0,reviewRates.getRatingDown());
    }

    @Test
    void  reportReviewAndMatch () throws NonExistentSourceException, NonExistentLocationException, NonExistentReviewException, NonExistentTitleException, UserAlreadyReviewTitle, NonExistentLanguageException {
        UserDTO user = new UserDTO(1,"pipo","bla",1);
        ReviewDTO reviewDTO = new ReviewDTO(null,1,"","",4,false,1,user);

        reviewService.save(reviewDTO);

        UserDTO userDto = new UserDTO(1,"quique","pepe",1);

        ReportDTO report= new ReportDTO(1,Reason.OFFENSIVE,"BLA BLA",userDto);

        ReviewReport reviewReport= reviewService.report(report);

        assertEquals(Reason.OFFENSIVE,reviewReport.getReason());
        assertEquals("pepe",reviewReport.getUser().getUserNick());
    }

    @Test
    void  addOneReviewWithExistentUserAndGetException () throws UserAlreadyReviewTitle, NonExistentSourceException, NonExistentLanguageException, NonExistentTitleException, NonExistentLocationException {
        UserDTO user = new UserDTO(2,"RepeatedUser","nick",1);
        ReviewDTO reviewDTO = new ReviewDTO(null,1,"","",4,false,1,user);
        reviewService.save(reviewDTO);


        UserDTO repeatedUser = new UserDTO(2,"RepeatedUser","nick",1);
        ReviewDTO otherReviewDTO = new ReviewDTO(null,1,"","",4,false,1,repeatedUser);

        assertThrows(UserAlreadyReviewTitle.class, () -> {
            reviewService.save(otherReviewDTO );
        });


    }
    @Test
    void  addOneReviewWithNonExistentTitleIdAndGetException () throws UserAlreadyReviewTitle, NonExistentSourceException, NonExistentLanguageException, NonExistentTitleException, NonExistentLocationException {
        UserDTO user = new UserDTO(2,"RepeatedUser","nick",1);
        ReviewDTO reviewDTO = new ReviewDTO(null,1000,"","",4,false,1,user);


        assertThrows(NonExistentTitleException.class, () -> {
            reviewService.save(reviewDTO);
        });
    }

    @Test
    void  rateAReviewThatNotExistsAndGetException () throws UserAlreadyReviewTitle, NonExistentSourceException, NonExistentLanguageException, NonExistentTitleException, NonExistentLocationException {
        UserDTO user = new UserDTO(2,"RepeatedUser","nick",1);
        RateDTO rate = new RateDTO(user,12,RateType.DOWN);

        assertThrows(NonExistentReviewException.class, () -> {
            reviewService.rate(rate);
        });
    }




}
