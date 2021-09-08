package ar.edu.unq.desapp.grupoj.backenddesappapi;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.FrontUserService;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.RegisterDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentSourceException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class BackendDesappApiFronUserTests {

        @Autowired
        private FrontUserService userService;

        @Test
        void retrieveAllUsersAndGetNone() {
            List<FrontUser> users = userService.findAll();
            assertFalse(users.isEmpty());
        }

        @Test
        void retrieveAllUsersAndGetOne() throws UserAlreadyExistsException, NonExistentSourceException {
            RegisterDTO user = new RegisterDTO(1,"alonso.em2@gmail.com","quique","123");
            userService.save(user);
            List<FrontUser> users = userService.findAll();
            assertEquals(2,users.size());
        }



    }

