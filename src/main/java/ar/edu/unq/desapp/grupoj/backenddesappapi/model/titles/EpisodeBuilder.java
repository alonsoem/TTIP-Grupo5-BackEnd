package ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles;

public class EpisodeBuilder {

    private Title title;
    private Integer seasonNumber;
    private Integer episodeNumber;


    public EpisodeBuilder(Title title, Integer seasonNumber, Integer episodeNumber){
        this.title= title;
        this.episodeNumber=episodeNumber;
        this.seasonNumber=seasonNumber;
    }
    public Episode toEpisode(Title parentTitle){
        Episode e = new Episode(title, parentTitle, seasonNumber,episodeNumber);
        return  e;
    }

    public Title toTitle(){return title;}

}
