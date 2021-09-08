package ar.edu.unq.desapp.grupoj.backenddesappapi.service;

import ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos.UserDTO;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentLocationException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentSourceException;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import ar.edu.unq.desapp.grupoj.backenddesappapi.repository.UserRepository;
import ar.edu.unq.desapp.grupoj.backenddesappapi.service.exceptions.NonExistentUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private SourceService sourceSrvc;

    @Autowired
    private LocationService locationSrvc;

    @Transactional
    public User getBySourceAndUserIdAndNickId(Integer sourceId, String userId,String userNick, Integer locationId) throws NonExistentSourceException, NonExistentLocationException {
        User user;
        try {
            user = getUser(sourceId, userId, userNick);
        }catch (NonExistentUserException e) {
            user = this.createUser(sourceId, userId, userNick, locationId);
        }
        repo.save(user);
        return user;
    }

    @Transactional
    public User getUser(Integer sourceId, String userId,String userNick) throws NonExistentSourceException, NonExistentUserException {
        Source source = sourceSrvc.getById(sourceId);
        UserDTO userDto = new UserDTO(sourceId,userId,userNick,0); //TODO locationId mando en cero y no se usa
        return repo.findBySourceAndUserIdAndUserNick(source,userId,userNick).orElseThrow(()->new NonExistentUserException(userDto));
    }


    public User createUser(Integer sourceId, String userId, String userNick, Integer locationId) throws NonExistentLocationException, NonExistentSourceException {
        Location location= locationSrvc.getById(locationId);
        Source source = sourceSrvc.getById(sourceId);
        User user = new User(source,userId,userNick,location);
        repo.save(user);
        return user;
    }
}
