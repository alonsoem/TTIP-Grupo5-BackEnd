package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Location;

public class LocationDTO {

    private Integer id;
    private String country;
    private String city;

    public LocationDTO(Integer id, String countryName, String cityName) {
        this.id = id;
        this.country = countryName;
        this.city = cityName;
    }

    public static LocationDTO fromModel(Location location) {
        return new LocationDTO(location.getId(), location.getCountry(), location.getCity());
    }

    public Integer getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }
}
