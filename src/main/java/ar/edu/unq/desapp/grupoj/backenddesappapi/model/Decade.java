package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Decade {

    @Id
    private String id;
    private Integer fromYear;
    private Integer toYear;

    public Decade(String name, Integer from, Integer to){
        this.id = name;
        this.fromYear = from;
        this.toYear = to;
    }

    protected Decade(){}

    public Integer getFrom() {
        return fromYear;
    }

    public Integer getTo() {
        return toYear;
    }

    public String getId() {
        return id;
    }


}
