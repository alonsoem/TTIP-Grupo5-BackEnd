package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.FrontUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FrontUserRepository extends JpaRepository<FrontUser, Integer> {
      List<FrontUser> findAll();
      Optional<FrontUser> findByUserName(String userName);
}

