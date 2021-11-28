package ar.edu.unq.ttip.alec.backend.repository;

import ar.edu.unq.ttip.alec.backend.model.Broker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, Integer> {

    List<Broker> findAll();
    @Query(
            value = "SELECT * FROM broker b INNER JOIN frontuser u " +
                    "ON u.id = b.owner_id " +
                    "WHERE b.is_public=true OR u.user_name = ?1",
            nativeQuery = true)
    List<Broker> findAllByIsPublicIsTrueOrOwner(String username);

    Optional<Broker> getBrokerById(Integer id);

    @Query(
            value = "SELECT * FROM broker b INNER JOIN frontuser u " +
                    "ON u.id = b.owner_id " +
                    "WHERE b.is_public=true and u.id = ?1",
            nativeQuery = true)
    List<Broker> findAllPublicByUser(Integer userId);


}



