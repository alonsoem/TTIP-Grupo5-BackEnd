package ar.edu.unq.ttip.alec.backend.service;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.Province;
import ar.edu.unq.ttip.alec.backend.repository.FrontUserRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.FrontUserDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.RegisterDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.UserAlreadyExistsException;

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



    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        frontUserRepo.save(new FrontUser("alonso.em@gmail.com","Enrique Alonso","123456", Province.TUCUMAN,true));
        frontUserRepo.save(new FrontUser("ljplatero@gmail.com","Leandro Platero","123456", Province.CABA,false));
    }

    @Transactional
    public List<FrontUser> findAll() {
        return frontUserRepo.findAll();
    }

    @Transactional
    public FrontUserDTO save(RegisterDTO frontuser) throws UserAlreadyExistsException {
        try {
            FrontUser user= frontuser.toModel();
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
