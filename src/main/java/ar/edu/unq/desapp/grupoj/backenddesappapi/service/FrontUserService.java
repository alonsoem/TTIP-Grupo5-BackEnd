package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.FrontUserRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.RegisterDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentSourceException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FrontUserService implements UserDetailsService {

    @Autowired
    private FrontUserRepository frontUserRepo;
    @Autowired
    private SourceService sourceService;

    @EventListener
    public void appReady(ApplicationReadyEvent event) throws NonExistentSourceException {

        frontUserRepo.save(new FrontUser("alonso.em@gmail.com","Enrique Alonso","4a7d1ed414474e4033ac29ccb8653d9b",sourceService.getById(1)));
    }

    @Transactional
    public List<FrontUser> findAll() {

        return frontUserRepo.findAll();
    }

    @Transactional
    public FrontUserDTO save(RegisterDTO frontuser) throws UserAlreadyExistsException, NonExistentSourceException {
        Source source = sourceService.getById(frontuser.getSourceId());
        try {
            FrontUser user = frontuser.toModel(source);
            frontUserRepo.save(user);
            return FrontUserDTO.fromModel(user);
        }catch(DuplicateKeyException e){
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public FrontUser loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<FrontUser> frontUser = frontUserRepo.findByUserName(username);
        frontUser.orElseThrow(() ->  new UsernameNotFoundException("Not Found: " + username));
        return frontUser.get();
    }
}
