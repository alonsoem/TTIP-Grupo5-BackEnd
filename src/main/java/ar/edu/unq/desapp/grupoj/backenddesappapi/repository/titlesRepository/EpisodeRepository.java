package ar.edu.unq.desapp.grupoj.backenddesappapi.repository.titlesRepository;


import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Episode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EpisodeRepository extends CrudRepository<Episode, Integer> {
    Iterable<Episode> findAll();
    //Iterable<Episode> findByParentId(Integer id);
    //Optional<Episode> getByTitleId(Integer id);

}


