package ar.edu.unq.desapp.grupoj.backenddesappapi.controllers;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.FrontUserDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.FrontUserService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.RegisterDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.UserAlreadyExistsException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.webservices.FrontUserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class FrontUserControllerTests {

    @Mock
    private FrontUserService service ;

    @InjectMocks
    private FrontUserController controller;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void frontUserControllerTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        List<FrontUser> list= new ArrayList<>();
        Source source = new Source("Test");
        FrontUser user= new FrontUser("quique","alonso.em@gmail.com","123456",source);
        list.add(user);
        when(service.findAll()).thenReturn(list);

        MockHttpServletResponse response= mvc.perform(get("/frontusers")).andExpect(status().isOk()).andReturn().getResponse();

        assertEquals("[{\"username\":\"quique\",\"name\":\"alonso.em@gmail.com\",\"active\":true}]",response.getContentAsString());

    }

    @Test
    public void registerFrontUserControllerTest() throws Exception, UserAlreadyExistsException {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        Source source = new Source("Test");
        FrontUserDTO user= new FrontUserDTO("quique@gmail.com","quique",true);
        when(service.save(Mockito.any())).thenReturn(user);


        RegisterDTO dtoRegister= new RegisterDTO(1,"quique@gmail.com","quique","123456");
        MockHttpServletResponse response= mvc.perform(post("/register")
                .content(asJsonString(dtoRegister))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn().getResponse();

        assertEquals(
                "{\"username\":\"quique@gmail.com\",\"name\":\"quique\",\"active\":true}",

                response.getContentAsString());
    }



    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
