package ar.edu.unq.desapp.grupoj.backenddesappapi.repository.titlesRepository;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TitleRepository extends CrudRepository<Title, Integer> {

    List<Title> findAll();
    Optional<Title> getByTitleId(Integer id);

}



