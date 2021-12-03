package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.SystemPropertyActiveProfileResolver;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.dtos.RuleDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(value="test", resolver=SystemPropertyActiveProfileResolver.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class RuleControllerTests {
	@Autowired
    private FrontUserService userservice;
	@Autowired
    private JwtUtil jwtTokenUtil;
	@Autowired
	private MockMvc mvc;
	private String token;

	@Autowired
	private ObjectMapper objectMapper;


	@BeforeAll
	public void setUp() {
		final UserDetails userDetails = userservice.loadUserByUsername("alonso.em@gmail.com");
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        this.token = "Bearer " + jwt;
	}

	@DisplayName("When requets rules without authenticate get error 403")
	@Test
	void whenNotAuthenticatedThenGetRulesReturns403() throws Exception {
		mvc.perform(get("/rule"))
	    	.andExpect(status().isForbidden());
	}

	@DisplayName("When request rules and have authenticated get all available rules")
	@Test
	void whenValidRequestThenGetAllRulesReturnsStatusCode200AndListOfRules() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/rule")
				.header("Authorization", this.token))
	    	.andExpect(status().isOk())
			.andReturn().getResponse();

		assertThat(response.getContentAsString()).contains("Sin Apartado");
		assertThat(response.getContentAsString()).contains("Es Apartado B y monto menor a 10");
		assertThat(response.getContentAsString()).contains("Es Apartado B y monto mayor a 10");
		assertThat(response.getContentAsString()).contains("Es Responsable Inscripto");
		assertThat(response.getContentAsString()).contains("Apartado A IVA");


	}

	@DisplayName("When request one rule get this rule")
	@Test
	void whenGetOneRuleGetThisRule() throws Exception {
        MvcResult result = mvc.perform(get("/rule/1").header("Authorization", this.token))
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = result.getResponse().getContentAsString();

		RuleDTO rule= objectMapper.readValue(responseBody, RuleDTO.class);

        assertEquals(rule.getName(),"New name rule");
		assertEquals(rule.getDescription(),"nueva descripcion");
		assertEquals((int) rule.getId(),1);
		assertEquals((int) rule.getPriority(),1);
		assertEquals(new ArrayList<String>(),rule.getWhen());
		assertEquals(new ArrayList<String>(), rule.getThen());
	}

	@DisplayName("When updates a Rule you get a Rule with same id and different values")
	@Test
	void updateRuleAndGetUpdatedRule() throws Exception {

		RuleDTO ruleNewValues= new RuleDTO("New name rule","nueva descripcion",null,null);
		String json = objectMapper.writeValueAsString(ruleNewValues);

		MvcResult resultUpdate = mvc.perform(put("/tax/1/rule/1")
						.header("Authorization", this.token)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andReturn();
		String responseBodyUpdate = resultUpdate.getResponse().getContentAsString();

		RuleDTO updatedRule= objectMapper.readValue(responseBodyUpdate, RuleDTO.class);

		assertEquals(updatedRule.getName(),"New name rule");
		assertEquals(updatedRule.getDescription(),"nueva descripcion");
		assertEquals((int) updatedRule.getId(),1);
		assertEquals((int) updatedRule.getPriority(),1);
		assertEquals(updatedRule.getWhen(),null);
		assertEquals(updatedRule.getThen(),null);
	}



}
