package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Source;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Iterable<User> findAll();
    Optional<User> findBySourceAndUserIdAndUserNick(Source source, String userId, String userNick);
    Iterable<User> findAllBySource(Source source);
    Iterable<User> findAllByLocation(Location location);

}


