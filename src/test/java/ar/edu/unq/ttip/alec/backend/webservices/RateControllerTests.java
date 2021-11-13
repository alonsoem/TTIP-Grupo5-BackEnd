package ar.edu.unq.ttip.alec.backend.webservices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import ar.edu.unq.ttip.alec.backend.SystemPropertyActiveProfileResolver;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;

@ActiveProfiles(value="test", resolver=SystemPropertyActiveProfileResolver.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RateControllerTests {
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
	void whenNotAuthenticatedThenGetAllTaxReturns403() throws Exception {
		mvc.perform(get("/rate"))
	    	.andExpect(status().isForbidden());
	}
	
	@Test
	void whenValidRequestThenGetAllTaxReturnsStatusCode200() throws Exception {
		mvc.perform(get("/rate").header("Authorization", this.token))
	    	.andExpect(status().isOk());
	}
	
	@Test
	void whenValidRequestThenGetAllTaxReturnsTheTaxesAsJSON() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/rate").header("Authorization", this.token))
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("IVA");
        assertThat(responseBody).contains("PAIS");
	}
}
