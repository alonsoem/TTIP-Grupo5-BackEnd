package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.SystemPropertyActiveProfileResolver;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.dtos.TaxDTO;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;
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
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles(value="test", resolver=SystemPropertyActiveProfileResolver.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class TaxControllerTests {
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

	@DisplayName("When get taxes without authenticate get error 403")
	@Test
	void whenNotAuthenticatedThenGetTaxesReturns403() throws Exception {
		mvc.perform(get("/broker/1/tax"))
	    	.andExpect(status().isForbidden());
	}

	@DisplayName("When request taxes and have authenticated get all available taxes")
	@Test
	void whenValidRequestThenGetAllTaxReturnsStatusCode200AndListOfTaxes() throws Exception {
		MockHttpServletResponse response = mvc.perform(get("/broker/1/tax")
				.header("Authorization", this.token))
	    	.andExpect(status().isOk())
			.andReturn().getResponse();

		assertThat(response.getContentAsString()).contains("Impuesto paÃ­s");
		assertThat(response.getContentAsString()).contains("IVA servicios digitales internacionales");
		assertThat(response.getContentAsString()).contains("Adelanto al impuesto a las Ganancias y los Bienes Personales");
		assertThat(response.getContentAsString()).contains("PercepciÃ³n IIBB a los servicios digitales del exterior");

	}

	@DisplayName("When request one tax get this tax")
	@Test
	void whenGetOneTaxGetThisTax() throws Exception {
        MvcResult result = mvc.perform(get("/broker/1/tax/2").header("Authorization", this.token))
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = result.getResponse().getContentAsString();

		TaxDTO tax= objectMapper.readValue(responseBody, TaxDTO.class);

        assertEquals("IVA servicios digitales internacionales", tax.getName());
		assertEquals("https://www.afip.gob.ar/iva/servicios-digitales/concepto.asp", tax.getUrl());
		assertEquals(2,(int) tax.getId());
		assertEquals(6,tax.getRules().size());
	}

	@DisplayName("When updates a Tax you get a tax with same id and different values")
	@Test
	void updateTaxAndGetUpdatedTax() throws Exception {

		String newNameValue="Gravamen Modificado";
		String newUrlValue="http://urlmodificada.com";
		TaxDTO  updateTax = new TaxDTO(newNameValue, newUrlValue,new ArrayList<>());
		String json = objectMapper.writeValueAsString(updateTax);

		MvcResult resultUpdate = mvc.perform(put("/broker/1/tax/3")
						.header("Authorization", this.token)
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();
		String responseBodyUpdate = resultUpdate.getResponse().getContentAsString();

		TaxDTO taxUpdated= objectMapper.readValue(responseBodyUpdate, TaxDTO.class);

		assertTrue(responseBodyUpdate.contains("Gravamen Modificado"));
		assertTrue("Gravamen Modificado"== taxUpdated.getName());
		assertTrue("http://urlmodificada.com"== taxUpdated.getUrl());
		assertTrue(3== taxUpdated.getId());
		assertTrue(1==taxUpdated.getRules().size());
	}



}
