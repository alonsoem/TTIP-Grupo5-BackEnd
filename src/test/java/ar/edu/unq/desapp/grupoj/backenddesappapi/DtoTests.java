package ar.edu.unq.desapp.grupoj.backenddesappapi;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.TitleType;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.TitleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DtoTests {

    @Test
    void createTitleDTO(){
        TitleDTO title= new TitleDTO(1,"titulo",60, TitleType.MOVIE,2021,2021,true,5,2.90);
        assertEquals(1,title.getTitleId());
        assertEquals(true,title.getAdult());
        assertEquals(2021,title.getEndYear());
        assertEquals(2021,title.getStartYear());
        assertEquals("titulo",title.getTitle());
        assertEquals(TitleType.MOVIE,title.getType());
        assertEquals(60,title.getDuration());
        assertEquals(5,title.getReviewCount());
        assertEquals(2.90, title.getAverageRating());
    }


}
