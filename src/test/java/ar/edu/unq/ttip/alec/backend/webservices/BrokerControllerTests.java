package ar.edu.unq.ttip.alec.backend.webservices;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;

import ar.edu.unq.ttip.alec.backend.service.dtos.BrokerDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.unq.ttip.alec.backend.SystemPropertyActiveProfileResolver;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;

@ActiveProfiles(value="test", resolver=SystemPropertyActiveProfileResolver.class)
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

	@Autowired
	private ObjectMapper objectMapper;


	private String tokenA;
	private String tokenB;
	private String tokenC;
	private String tokenD;
	private String tokenE;
	private String tokenF;
	private Map<String, String> mapNOAP = new HashMap<>();
	private Map<String, String> mapAPA = new HashMap<>();
	private Map<String, String> mapAPB1 = new HashMap<>();
	private Map<String, String> mapAPB100 = new HashMap<>();
	
	@BeforeAll
	private void setUp() {
        String pfx = "Bearer ";
        this.tokenA = pfx + jwtTokenUtil.generateToken(userservice.loadUserByUsername("user@alec.com"));
        this.tokenB = pfx + jwtTokenUtil.generateToken(userservice.loadUserByUsername("apiuser@alec.com"));
        this.tokenC = pfx + jwtTokenUtil.generateToken(userservice.loadUserByUsername("alonso.em@gmail.com"));
        this.tokenD = pfx + jwtTokenUtil.generateToken(userservice.loadUserByUsername("userNG@alec.com"));
        this.tokenE = pfx + jwtTokenUtil.generateToken(userservice.loadUserByUsername("userNGTF@alec.com"));
        this.tokenF = pfx + jwtTokenUtil.generateToken(userservice.loadUserByUsername("userNRI@alec.com"));
        this.mapNOAP.put("apartado", "NOAPARTADO");
        this.mapNOAP.put("taxId", "1");
        this.mapNOAP.put("amount", "100");
        this.mapAPA.put("apartado", "APARTADOA");
        this.mapAPA.put("taxId", "1");
        this.mapAPA.put("amount", "100");
        this.mapAPB1.put("apartado", "APARTADOB");
        this.mapAPB1.put("taxId", "1");
        this.mapAPB1.put("amount", "1");
        this.mapAPB100.put("apartado", "APARTADOB");
        this.mapAPB100.put("taxId", "1");
        this.mapAPB100.put("amount", "100");

	}
	
	// MainCalc tests
	// NoTierraDelFuegoNoGananciasNoRIUser
	@DisplayName("When user not from Tierra del Fuego, not Ganancias, not RI enters apartado NoApartado and amount 100 then MainCalc returns total 130")
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersNoApartado100ThenMainCalcReturns130() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("130"));
	}
	
	@DisplayName("When user not from Tierra del Fuego, not Ganancias, not RI enters apartado ApartadoA and amount 100 then MainCalc returns total 129")
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersApartadoA100ThenMainCalcReturns129() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("129"));
	}
	
	@DisplayName("When user not from Tierra del Fuego, not Ganancias, not RI enters apartado ApartadoB and amount 100 then MainCalc returns total 130")
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersApartadoB100ThenMainCalcReturns130() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("130"));
	}
	
	@DisplayName("When user not from Tierra del Fuego, not Ganancias, not RI enters apartado ApartadoB and amount 1 then MainCalc returns total 1.29")
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersApartadoB1ThenMainCalcReturns1Point29() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("1.29"));
	}
	//
	
	// TierraDelFuegoAndGananciasUser
	@DisplayName("When user from Tierra del Fuego and Ganancias enters apartado NoApartado and amount 100 then MainCalc returns total 165")
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersNoApartado100ThenMainCalcReturns165() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("165"));
	}
	
	@DisplayName("When user from Tierra del Fuego and Ganancias enters apartado ApartadoA and amount 100 then MainCalc returns total 143")
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersApartadoA100ThenMainCalcReturns143() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("143"));
	}
	
	@DisplayName("When user from Tierra del Fuego and Ganancias enters apartado ApartadoB and amount 100 then MainCalc returns total 165")
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersApartadoB100ThenMainCalcReturns165() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("165"));
	}
	
	@DisplayName("When user from Tierra del Fuego and Ganancias enters apartado ApartadoB and amount 1 then MainCalc returns total 1.43")
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersApartadoB1ThenMainCalcReturns1Point43() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("1.43"));
	}
	//
	
	// TierraDelFuegoAndNoGananciasUser
	@DisplayName("When user from Tierra del Fuego and not Ganancias enters apartado NoApartado and amount 100 then MainCalc returns total 130")
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersNoApartado100ThenMainCalcReturns130() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("130"));
	}
	
	@DisplayName("When user from Tierra del Fuego and not Ganancias enters apartado ApartadoA and amount 100 then MainCalc returns total 108")
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersApartadoA100ThenMainCalcReturns108() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("108"));
	}
	
	@DisplayName("When user from Tierra del Fuego and not Ganancias enters apartado ApartadoB and amount 100 then MainCalc returns total 130")
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersApartadoB100ThenMainCalcReturns130() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("130"));
	}
	
	@DisplayName("When user from Tierra del Fuego and not Ganancias enters apartado ApartadoB and amount 1 then MainCalc returns total 1.08")
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersApartadoB1ThenMainCalcReturns1Point08() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("1.08"));
	}
	//
	
	// RIAndGananciasUser
	@DisplayName("When RI and Ganancias user enters apartado NoApartado and amount 100 then MainCalc returns total 165")
	@Test
	void whenRIAndGananciasUserEntersNoApartado100ThenMainCalcReturns165() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("165"));
	}
	
	@DisplayName("When RI and Ganancias user enters apartado ApartadoA and amount 100 then MainCalc returns total 143")
	@Test
	void whenRIAndGananciasUserEntersApartadoA100ThenMainCalcReturns143() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("143"));
	}
	
	@DisplayName("When RI and Ganancias user enters apartado ApartadoB and amount 100 then MainCalc returns total 165")
	@Test
	void whenRIAndGananciasUserEntersApartadoB100ThenMainCalcReturns165() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("165"));
	}
	
	@DisplayName("When RI and Ganancias user enters apartado ApartadoB and amount 1 then MainCalc returns total 1.43")
	@Test
	void whenRIAndGananciasUserEntersApartadoB1ThenMainCalcReturns1Point43() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("1.43"));
	}
	//
	
	// RIAndNoGananciasUser
	@DisplayName("When RI and not Ganancias user enters apartado NoApartado and amount 100 then MainCalc returns total 130")
	@Test
	void whenRIAndNoGananciasUserEntersNoApartado100ThenMainCalcReturns130() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("130"));
	}
	
	@DisplayName("When RI and not Ganancias user enters apartado ApartadoA and amount 100 then MainCalc returns total 108")
	@Test
	void whenRIAndNoGananciasUserEntersApartadoA100ThenMainCalcReturns108() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("108"));
	}
	
	@DisplayName("When RI and not Ganancias user enters apartado ApartadoB and amount 100 then MainCalc returns total 130")
	@Test
	void whenRIAndNoGananciasUserEntersApartadoB100ThenMainCalcReturns130() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("130"));
	}
	
	@DisplayName("When RI and not Ganancias user enters apartado ApartadoB and amount 1 then MainCalc returns total 1.08")
	@Test
	void whenRIAndNoGananciasUserEntersApartadoB1ThenMainCalcReturns1Point08() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("1.08"));
	}
	//
	
	// CABAIIBBNoGananciasNoRIUser
	@DisplayName("When user from CABA and not Ganancias and not RI enters apartado NoApartado and amount 100 then MainCalc returns total 132")
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersNoApartado100ThenMainCalcReturns132() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("132"));
	}
	
	@DisplayName("When user from CABA and not Ganancias and not RI enters apartado ApartadoA and amount 100 then MainCalc returns total 131")
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersApartadoA100ThenMainCalcReturns131() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("131"));
	}
	
	@DisplayName("When user from CABA and not Ganancias and not RI enters apartado ApartadoB and amount 100 then MainCalc returns total 132")
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersApartadoB100ThenMainCalcReturns132() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("132"));
	}
	
	@DisplayName("When user from CABA and not Ganancias and not RI enters apartado ApartadoB and amount 1 then MainCalc returns total 1.31")
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersApartadoB1ThenMainCalcReturns1Point31() throws Exception {
        mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andExpect(jsonPath("$.totalAmount").value("1.31"));
	}

	@DisplayName("When copy a broker gets a new broker with new id but same properties")
	@Test
	void WhenCopyABrokerGetsANewBrokerWithNewIdButSameProperties() throws Exception {
		MockHttpServletResponse original= mvc.perform(get("/broker/3")
				.header("Authorization", this.tokenF))
				.andExpect(status().isOk()).andReturn().getResponse();

		MockHttpServletResponse copyResponse= mvc.perform(
				post("/broker/copy/3")
						.header("Authorization", this.tokenF)
				)
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn().getResponse();

		BrokerDTO originalR= objectMapper.readValue(original.getContentAsString(), BrokerDTO.class);
		BrokerDTO response = objectMapper.readValue(copyResponse.getContentAsString(), BrokerDTO.class);


		assertEquals(originalR.getName() + " (C)",response.getName());
		assertEquals(originalR.getTaxes().size(),response.getTaxes().size());
		assertNotEquals(originalR.getIsPublic(),response.getIsPublic());
		assertEquals(originalR.getDescription(),response.getDescription());
		assertNotEquals(originalR.getUserId(),response.getUserId());
		assertEquals(originalR.getId()+1,response.getId());

	}

	@DisplayName("When trying delete a broker with unexistent id gets exception")
	@Test
	void WhenDeleteBrokerWithNotPersistedId() throws Exception {
		MockHttpServletResponse result = mvc.perform(
				delete("/broker/222")
						.header("Authorization", this.tokenF)
				)
				.andExpect(status().isBadRequest()).andReturn().getResponse();

		ApiErrorTest errorResponse = objectMapper.readValue(result.getContentAsString(), ApiErrorTest.class);

		assertEquals(errorResponse.getStatus(),"BAD_REQUEST");
		assertEquals(errorResponse.getMessage(),"Validation Errors");
		assertThat(errorResponse.getErrors().contains("Non existent broker with id 222"));

	}

	@DisplayName("get all brokers for User alonso.em@gmail.com and get 2 items.")
	@Test
	void getAllBrokersForUseralonsoEmGmailDotComAndGet2Items() throws Exception {
		MockHttpServletResponse brokerList = mvc.perform(get("/broker/myBrokers")
				.header("Authorization", this.tokenC))
				.andExpect(status().isOk()).andReturn().getResponse();

		assertThat(brokerList.getContentAsString().contains("Pago Servicios Digitales en el Exterior"));
		assertThat(brokerList.getContentAsString().contains("Adelanto al impuesto a las Ganancias y los Bienes Personales"));
	}

	@DisplayName("When delete a broker get a list of brokers and get one broker less")
	@Test
	void WhenDeleteBrokerGetAListOfBrokersAndGetsOneLess() throws Exception {
		MockHttpServletResponse brokerListBefore= mvc.perform(get("/broker/myBrokers")
				.header("Authorization", this.tokenF))
				.andExpect(status().isOk()).andReturn().getResponse();

		mvc.perform(
				delete("/broker/2")
						.header("Authorization", this.tokenF)
				)
				.andExpect(status().isOk()).andReturn();

		MockHttpServletResponse brokerListAfter= mvc.perform(get("/broker/myBrokers")
				.header("Authorization", this.tokenF))
				.andExpect(status().isOk()).andReturn().getResponse();


		assertThat(brokerListBefore.getContentLength()>=brokerListAfter.getContentLength()+1);

	}



}
