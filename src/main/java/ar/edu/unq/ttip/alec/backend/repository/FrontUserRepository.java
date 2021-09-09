package ar.edu.unq.ttip.alec.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unq.ttip.alec.backend.model.FrontUser;

import java.util.List;
import java.util.Optional;


@Repository
public interface FrontUserRepository extends JpaRepository<FrontUser, Integer> {
      List<FrontUser> findAll();
      Optional<FrontUser> findByUserName(String userName);
}

