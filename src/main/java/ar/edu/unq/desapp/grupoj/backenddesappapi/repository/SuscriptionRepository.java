package ar.edu.unq.desapp.grupoj.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Suscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SuscriptionRepository extends CrudRepository<Suscription, Integer> {
    List<Suscription> getAllByTitleId(Integer titleId);
    Suscription getByUrl(String url);

    List<Suscription> getAllByUrl(String url);
}


