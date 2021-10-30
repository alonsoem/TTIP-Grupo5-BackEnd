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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
	private String tokenA;
	private String tokenB;
	private String tokenC;
	private String tokenD;
	private String tokenE;
	private String tokenF;
	private Map<String, String> mapNOAP = new HashMap<String, String>();
	private Map<String, String> mapAPA = new HashMap<String, String>();
	private Map<String, String> mapAPB1 = new HashMap<String, String>();
	private Map<String, String> mapAPB100 = new HashMap<String, String>();
	
	@BeforeAll
	public void setUp() {
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
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersNoApartado100_thenMainCalcReturns130() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
	
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersApartadoA100_thenMainCalcReturns129() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("129");
	}
	
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersApartadoB100_thenMainCalcReturns130() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
	
	@Test
	void whenNoTierraDelFuegoNoGananciasNoRIUserEntersApartadoB1_thenMainCalcReturns1Point29() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenB)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("1.29");
	}
	//
	
	// TierraDelFuegoAndGananciasUser
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersNoApartado100_thenMainCalcReturns165() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("165");
	}
	
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersApartadoA100_thenMainCalcReturns143() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("143");
	}
	
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersApartadoB100_thenMainCalcReturns165() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("165");
	}
	
	@Test
	void whenTierraDelFuegoAndGananciasUserEntersApartadoB1_thenMainCalcReturns1Point43() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenA)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("1.43");
	}
	//
	
	// TierraDelFuegoAndNoGananciasUser
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersNoApartado100_thenMainCalcReturns130() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
	
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersApartadoA100_thenMainCalcReturns108() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("108");
	}
	
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersApartadoB100_thenMainCalcReturns130() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
	
	@Test
	void whenTierraDelFuegoAndNoGananciasUserEntersApartadoB1_thenMainCalcReturns1Point08() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenE)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("1.08");
	}
	//
	
	// RIAndGananciasUser
	@Test
	void whenRIAndGananciasUserEntersNoApartado100_thenMainCalcReturns165() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("165");
	}
	
	@Test
	void whenRIAndGananciasUserEntersApartadoA100_thenMainCalcReturns143() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("143");
	}
	
	@Test
	void whenRIAndGananciasUserEntersApartadoB100_thenMainCalcReturns165() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("165");
	}
	
	@Test
	void whenRIAndGananciasUserEntersApartadoB1_thenMainCalcReturns1Point43() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenC)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("1.43");
	}
	//
	
	// RIAndNoGananciasUser
	@Test
	void whenRIAndNoGananciasUserEntersNoApartado100_thenMainCalcReturns130() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
	
	@Test
	void whenRIAndNoGananciasUserEntersApartadoA100_thenMainCalcReturns108() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("108");
	}
	
	@Test
	void whenRIAndNoGananciasUserEntersApartadoB100_thenMainCalcReturns130() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("130");
	}
	
	@Test
	void whenRIAndNoGananciasUserEntersApartadoB1_thenMainCalcReturns1Point08() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenD)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("1.08");
	}
	//
	
	// CABAIIBBNoGananciasNoRIUser
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersNoApartado100_thenMainCalcReturns132() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapNOAP))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("132");
	}
	
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersApartadoA100_thenMainCalcReturns131() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapAPA))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("131");
	}
	
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersApartadoB100_thenMainCalcReturns132() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapAPB100))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("132");
	}
	
	@Test
	void whenCABAIIBBNoGananciasNoRIUserEntersApartadoB1_thenMainCalcReturns1Point31() throws Exception {     
        MvcResult mvcResult = mvc.perform(
        	post("/broker/calculate")
        		.header("Authorization", this.tokenF)
        		.content(mapper.writeValueAsString(this.mapAPB1))
        		.contentType(MediaType.APPLICATION_JSON)
        )
	    	.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
	    	.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();
        
        assertThat(responseBody).contains("1.31");
	}
	//
}
