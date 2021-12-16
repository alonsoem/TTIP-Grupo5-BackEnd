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

    //List<Broker> findAll();
    Optional<Broker> getBrokerById(Integer id);

    @Query(
            value = "SELECT * FROM broker b INNER JOIN frontuser u " +
                    "ON u.id = b.owner_id " +
                    "WHERE u.user_name = ?1",
            nativeQuery = true)
    List<Broker> findAllByOwner(String username);

    @Query(
            value = "SELECT * FROM broker b INNER JOIN frontuser u " +
                    "ON u.id = b.owner_id " +
                    "WHERE b.is_public=true and u.id = ?1",
            nativeQuery = true)
    List<Broker> findAllPublicByUser(Integer userId); //busqueda de calculadoras por userid publicas


    List<Broker> findAllByIsPublicIsTrue();


    @Query(
            value = "select b.*, s.invocations from broker b " +
                    "left join statistics s on b.id=s.broker_id " +
                    "Where b.is_public=true " +
                    "order by s.invocations desc " +
                    "limit 10",
            nativeQuery = true)
    List<Broker> getTop10BrokersByStatistics();


}



