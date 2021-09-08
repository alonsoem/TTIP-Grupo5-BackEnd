package ar.edu.unq.desapp.grupoj.backenddesappapi.controllers;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.LocationService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.webservices.LocationController;
import org.junit.jupiter.api.BeforeEach;
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
public class LocationControllerTests {

    @Mock
    private LocationService service ;

    @InjectMocks
    private LocationController controller;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void locationControllerTest() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        List<Location> list= new ArrayList<>();
        Location location= new Location("Argentina","La Plata");
        list.add(location);
        when(service.findAll()).thenReturn(list);

        MockHttpServletResponse response= mvc.perform(get("/location")).andExpect(status().isOk()).andReturn().getResponse();

        assertEquals("[{\"id\":null,\"country\":\"Argentina\",\"city\":\"La Plata\"}]",response.getContentAsString());
    }

}
