package ar.edu.unq.desapp.grupoj.backenddesappapi.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Suscription {

    //private Source source;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer titleId;
    private String url;

    protected Suscription(){}

    public Suscription (Integer aTitleId, String url){
        //this.source=aSource;git
        this.titleId=aTitleId;
        this.url = url;
    }


    public Integer getTitleId() {
        return titleId;
    }

    public String getUrl() {
        return url;
    }

}
