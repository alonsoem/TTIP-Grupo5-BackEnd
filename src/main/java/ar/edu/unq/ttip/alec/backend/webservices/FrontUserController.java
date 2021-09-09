package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.AuthenticationRequest;
import ar.edu.unq.ttip.alec.backend.model.AuthenticationResponse;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.dtos.FrontUserDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.RegisterDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.UserAlreadyExistsException;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins ="*")
@RestController
@EnableAutoConfiguration

public class FrontUserController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private FrontUserService service;

    @GetMapping("/frontusers")
    @Description("All FrontUsers list")
    public ResponseEntity<List<FrontUserDTO>> getAllFrontUsers() {
        return ResponseEntity.ok(service.findAll().stream().map(user-> FrontUserDTO.fromModel(user)).collect(Collectors.toList()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody RegisterDTO registerRequest) throws UserAlreadyExistsException {
        return new ResponseEntity(
                service.save(registerRequest),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


}