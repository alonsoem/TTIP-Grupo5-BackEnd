package ar.edu.unq.ttip.alec.backend.webservices;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.service.dtos.*;
import ar.edu.unq.ttip.alec.backend.service.FrontUserService;
import ar.edu.unq.ttip.alec.backend.service.exceptions.UserAlreadyExistsException;
import ar.edu.unq.ttip.alec.backend.service.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@Api(value="/frontusers", tags="Front User Controller",description = "Manage ALEC Front Users")
public class FrontUserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private FrontUserService service;
    
    @GetMapping("/frontusers")
    @ApiOperation("Get all Front Users")
    public ResponseEntity<List<FrontUserDTO>> getAllFrontUsers() {
        return ResponseEntity.ok(service.findAll().stream().map(user-> FrontUserDTO.fromModel(user)).collect(Collectors.toList()));
    }
    
    @GetMapping("/frontuser")
    @ApiOperation("Get one User by username")
    public ResponseEntity<FrontUserDTO> getFrontUser(@RequestParam(value = "username") String userName) {
        return ResponseEntity.ok(FrontUserDTO.fromModel(service.loadUserByUsername(userName)));
    }

    @GetMapping("/frontuser/{userId}")
    @ApiOperation("Get one User by username")
    public ResponseEntity<UserDTO> getFrontUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(UserDTO.fromModel(service.getUserMinDetails(userId)));
    }
    
    @PostMapping("/frontuser")
    @ApiOperation("Update User")
    public ResponseEntity<FrontUserDTO> updateFrontUser(@RequestBody FrontUserDTO updateRequest) {
        return ResponseEntity.ok(FrontUserDTO.fromModel(service.updateUser(updateRequest)));
    }

    @PostMapping("/register")
    @ApiOperation("Allow to register a new Front User")
    public ResponseEntity<FrontUserDTO> saveUser(@Valid @RequestBody RegisterDTO registerRequest) throws UserAlreadyExistsException {
        return new ResponseEntity<FrontUserDTO>(
            service.save(registerRequest),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/authenticate")
    @ApiOperation("Allow to authenticate a Front User")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = service.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
//        return ResponseEntity.ok(new AuthenticationResponse(jwt));
        return ResponseEntity.ok(new AuthResponseDTO(((FrontUser) userDetails).getId(),new AuthenticationResponse(jwt).getJwt()));
    }
}
