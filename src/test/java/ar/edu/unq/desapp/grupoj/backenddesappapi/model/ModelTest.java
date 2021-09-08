package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Cast;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Job;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.cast.Person;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ModelTest {


    @Test
    public void setAndGetReviewRateValues(){
        User user = Mockito.mock(User.class);
        Review review = Mockito.mock(Review.class);
        ReviewRate rate = new ReviewRate(RateType.UP,user,review);
        assertEquals(null,rate.getId());
        assertEquals(review,rate.getReview());
        assertEquals(RateType.UP,rate.getType());
        assertEquals(user, rate.getUser());
    }

    @Test
    public void setAndGetCastValues(){
        Person person = new Person("Pepe");
        Cast cast = new Cast(person, Job.ACTOR);

        assertEquals(null,cast.getId());
        assertEquals(Job.ACTOR, cast.getJob());
        assertEquals(person.getName(), cast.getPerson().getName());
        assertEquals(person.getId(), cast.getPerson().getId());
    }



}
