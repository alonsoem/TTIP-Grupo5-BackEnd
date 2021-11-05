package ar.edu.unq.ttip.alec.backend.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;
import ar.edu.unq.ttip.alec.backend.model.enumClasses.Province;
import ar.edu.unq.ttip.alec.backend.repository.FrontUserRepository;
import ar.edu.unq.ttip.alec.backend.service.dtos.FrontUserDTO;
import ar.edu.unq.ttip.alec.backend.service.dtos.RegisterDTO;
import ar.edu.unq.ttip.alec.backend.service.exceptions.UserAlreadyExistsException;


@Service
public class FrontUserService implements UserDetailsService {


    @Autowired
    private FrontUserRepository frontUserRepo;

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

    @Transactional
	public FrontUser updateUser(FrontUserDTO updateRequest) throws UsernameNotFoundException {
		FrontUser user = this.loadUserByUsername(updateRequest.getUsername());
		
		Province prov = updateRequest.getProvince();
		if (!Objects.isNull(prov)) {
			user.setProvince(prov);
		}
		Boolean ri = updateRequest.getIsResponsableInscripto();
		if (!Objects.isNull(ri)) {
			user.setResponsableInscripto(ri);
		}
		Boolean gybp = updateRequest.getIsgananciasYBienesP();
		if (!Objects.isNull(gybp)) {
			user.setGananciasYBienesP(gybp);
		}
		
		frontUserRepo.save(user);
		
		return user;
	}
}
