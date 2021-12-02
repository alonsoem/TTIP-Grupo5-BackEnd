package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.SystemPropertyActiveProfileResolver;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.dtos.AuthenticationRequest;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value="test", resolver=SystemPropertyActiveProfileResolver.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class FrontUserControllerTests {
	@Autowired
    private FrontUserService userservice;
	@Autowired
    private JwtUtil jwtTokenUtil;
	@Autowired
	private MockMvc mvc;
	private String token;
	
	@BeforeAll
	public void setUp() {
		final UserDetails userDetails = userservice.loadUserByUsername("user@alec.com");
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        this.token = "Bearer " + jwt;
	}
	
	// Ratelist tests
	@Test
	void whenNotAuthenticatedThenGetAllFrontUsersReturns403() throws Exception {
		mvc.perform(get("/frontusers"))
	    	.andExpect(status().isForbidden());
	}
	
	@Test
	void whenValidRequestThenGetAllFrontUsersReturnsStatusCode200() throws Exception {
		mvc.perform(get("/frontusers").header("Authorization", this.token))
	    	.andExpect(status().isOk());
	}
	
	@Test
	void whenValidRequestThenGetAllFrontUsersReturnsTheUsersAsJSON() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/frontusers").header("Authorization", this.token))
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody.length()==7);
	}
	@Test
	void whenGetOneFrontUserReturnsTheUserAsJSON() throws Exception {
		Integer userId=1;
		MvcResult mvcResult = mvc.perform(get("/frontuser/"+userId).header("Authorization", this.token))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();
		String responseBody = mvcResult.getResponse().getContentAsString();

		assertThat(responseBody).contains("{\"id\":1,\"name\":\"Enrique Alonso\"}");
	}

	@Test
	void whenAuthorizeUserGetUserIdAndToken() throws Exception {
		AuthenticationRequest authRequest = new AuthenticationRequest("userNRI@alec.com","e10adc3949ba59abbe56e057f20f883e");
		MvcResult mvcResult = mvc.perform(post("/authenticate")
				.content(asJsonString(authRequest))
				.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
				.andReturn();
		String responseBody = mvcResult.getResponse().getContentAsString();

		assertThat(responseBody).contains("\"id\":4");
	}

	private static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
