package ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table (name="episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Title title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Title parent;

    private Integer seasonNumber;
    private Integer episodeNumber;

    public Episode(Title title,Title parent, Integer seasonNumber, Integer episodeNumber) {
        this.episodeNumber=episodeNumber;
        this.seasonNumber=seasonNumber;
        this.parent=parent;
        this.title=title;

    }
    protected Episode(){}

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public Title getParent() {
        return parent;
    }

    public Title getTitle() {
        return title;
    }

    public Integer getId() {
        return id;
    }
}
