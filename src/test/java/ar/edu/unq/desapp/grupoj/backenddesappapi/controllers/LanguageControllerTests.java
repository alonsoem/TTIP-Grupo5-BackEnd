package ar.edu.unq.desapp.grupoj.backenddesappapi.controllers;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Language;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.LanguageService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.webservices.LanguageController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class LanguageControllerTests {

    @Mock
    private LanguageService service ;

    @InjectMocks
    private LanguageController controller;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void languagueControllerTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        List<Language> langs= new ArrayList<>();
        Language language = new Language("ARMENIO");
        langs.add(language);
        when(service.findAll()).thenReturn(langs);
       MockHttpServletResponse response= mvc.perform(get("/language")).andExpect(status().isOk()).andReturn().getResponse();

       assertEquals("[{\"id\":null,\"name\":\"ARMENIO\"}]",response.getContentAsString());
    }

}