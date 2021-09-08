package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    protected Language() {
    }

    public Language(String languageName) {
        this.value = languageName;
    }

    public String getValue() {
        return value;
    }

    public Integer getId() {
        return id;
    }
}
