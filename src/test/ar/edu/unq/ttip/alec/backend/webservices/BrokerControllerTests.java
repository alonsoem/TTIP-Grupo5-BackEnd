package ar.edu.unq.ttip.alec.backend.webservices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.HashMap;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BrokerControllerTests {
	@Autowired
    private FrontUserService userservice;
	@Autowired
    private JwtUtil jwtTokenUtil;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private MockMvc mvc;
	private String token;
	
	@BeforeAll
	public void setUp() {
		final UserDetails userDetails = userservice.loadUserByUsername("user@alec.com");
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        this.token = "Bearer " + jwt;
	}
	
	// MainCalc tests
	@Test
	void whenTierraDelFuegoUserEntersNoApartado100_thenMainCalcReturns130() throws Exception {     
		Map<String, String> map = new HashMap<String, String>();
		map.put("amount", "100");
		map.put("apartado", "NOAPARTADO");
		map.put("taxId", "1");
		
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.token)
        		.content(mapper.writeValueAsString(map))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
}
