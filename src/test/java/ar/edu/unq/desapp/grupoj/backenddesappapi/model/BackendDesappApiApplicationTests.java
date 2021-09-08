package ar.edu.unq.desapp.grupoj.backenddesappapi.model;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentCriticException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentDecadeException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentLocationException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentSourceException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.UserService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.SourceService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.DecadeService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.CriticService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.RateDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest

class BackendDesappApiApplicationTests {

	@Autowired
	private UserService userSrvc;

	@Autowired
	private SourceService sourceSrvc;
	@Autowired
	private DecadeService decadeSrvc;

	@Autowired
	private CriticService criticSrvc;

	@Test
	void ratingThreeInReviewReturnsThree() {
		User user = Mockito.mock(User.class);
		Language lang = new Language("Espanol");
		Review review = new Review(1, user,"Maso, para un domingo zafa",
				"pochoclera",3,true, lang);
		assertEquals(3, review.getRating());
	}

	@Test
	void getUserCriticNamedJoeReturnsJoe(){
		Source source = new Source("NETFLIX");
		Location location = new Location ("Argentina","City Bell");
		Critic critic = new Critic(source,"Joe",location);
		assertEquals("Joe", critic.getUserId());
	}


	@Test
	void espanolLanguageReturnsValueEspanol(){
		Language spanish = new Language("Español");
		assertEquals("Español",spanish.getValue());
	}

	@Test
	void locationCOuntryItalyReturnsItaly(){
		Location location = new Location("italy","Parma");
		assertEquals("italy",location.getCountry());
	}

	@Test
	void locationCityParmaReturnsParma(){
		Location location = new Location("italy","Parma");
		assertEquals("Parma",location.getCity());
	}
	@Test
	void rateDtoReviewId123Returns123(){
		UserDTO user = new UserDTO(1,"pepe.test@gmail.com",
				"pepito10",123);
		RateType rateType = RateType.UP;
		RateDTO rateDTO = new RateDTO(user,123, rateType);
		assertEquals(123, rateDTO.reviewId);
	}

	@Test
	void userServiceTest1() throws NonExistentLocationException, NonExistentSourceException {
		;Source source = Mockito.mock(Source.class);
		Location location = Mockito.mock(Location.class);
		User user = new User(source,"testUser","test1",location);
		User retrievedUser= userSrvc.getBySourceAndUserIdAndNickId(1,"testUser","test1",1);
		assertEquals("testUser", retrievedUser.getUserId());
		assertEquals("test1", retrievedUser.getUserNick());

	}


	@Test
	void getOneForCriticService() throws NonExistentSourceException, NonExistentLocationException, NonExistentCriticException {

		Critic retrievedCritic= criticSrvc.getBySourceAndCriticId(1,"ventura",1);
		assertEquals("Netflix-2", retrievedCritic.getSource().getName());
		assertEquals("ventura", retrievedCritic.getUserId());


	}

	@Test
	void retrieveOneUserFromService() throws NonExistentSourceException {

		Source sourceRetrieved = sourceSrvc.getById(1);
		assertEquals("Netflix-2", sourceRetrieved.getName());
		assertEquals(1, sourceRetrieved.getId());
	}

	@Test
	void retrieveCriticWithUnknownSourceFromServiceAndGetSourceException() {
		Exception exception = assertThrows(NonExistentSourceException.class,
				()->criticSrvc.getBySourceAndCriticId(999,"pepe",1)
		);

		String expectedMessage = "There is no source with ID 999";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void retrieveUserWithUnknownSourceFromServiceAndGetLocationException() {
		Exception exception = assertThrows(NonExistentLocationException.class,
				()->userSrvc.getBySourceAndUserIdAndNickId(1,"pepe","pepe",999)
		);

		String expectedMessage = "There is no Location with ID 999";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void retrieveAllUsersFromService(){

		List<String> sources =sourceSrvc.findAll().stream()
				.map(source->source.getName()).collect(Collectors.toList());

		assertEquals(6, sources.size());
		assertTrue(sources.contains("Netflix"));
		assertTrue(sources.contains("Disney+"));
		assertTrue(sources.contains("Amazon Prime Video"));
		assertTrue(sources.contains("Paramount"));
		assertTrue(sources.contains("HBO Go"));
		assertTrue(sources.contains("Netflix-2"));

	}

	@Test
	void retrieveOneDecadeFromService() throws NonExistentDecadeException {
		Decade decadeRetrieved = decadeSrvc.getById("D80");
		assertEquals(1980, decadeRetrieved.getFrom());
		assertEquals(1989, decadeRetrieved.getTo());
	}

	@Test
	void retrieveAllDecadeFromService() {

		List<Decade> decades = decadeSrvc.findAll();
		assertEquals(5, decades.size());


	}


	@Test
	public void testReview9(){
		User user = Mockito.mock(User.class);
		Language language = Mockito.mock(Language.class);
		Review review = new Review(9,user,"Prueba 1","texto extendido",3,true,language);

		assertEquals(0,review.getReviewRateInt());
		assertEquals(language,review.getLanguage());
		assertEquals(user,review.getUser());
		assertEquals(true,review.getSpoilerAlert());
				//review.getDate() //Mock de date
		assertEquals(ReviewType.NORMAL, review.getType());
		assertEquals("Prueba 1",review.getText());
		assertEquals("texto extendido",review.getTextExtended());
	}




}
