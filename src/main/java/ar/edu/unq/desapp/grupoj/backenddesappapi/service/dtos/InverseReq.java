package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.RateType;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.titles.Genre;

import java.util.List;

public class InverseReq {

    public List<String> decade;
    public RateType rating;
    public Integer minStars;
    public List<String> actors;
    public List<Genre> genres;

}
