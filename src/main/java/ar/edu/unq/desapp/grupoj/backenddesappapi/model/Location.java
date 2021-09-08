package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;
    private String city;

    protected Location(){}

    public Location (String countryName, String cityName){
        this.country=countryName;
        this.city=cityName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Integer getId() {
        return id;
    }

}
