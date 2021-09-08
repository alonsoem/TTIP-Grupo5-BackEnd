package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Language;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.ReviewPremium;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.ReviewType;
import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

@Validated
public class ReviewPremiumDTO {

    @NotNull(message = "Title Id cannot be null")
    public Integer titleId;
    @NotBlank(message = "Text cannot be null")
    public String text;
    public String textExtended;

    @Min(value=0,message = "Rating must be between 0..5")
    @Max(value=5,message="Rating must be between 0..5")
    public Integer rating;
    public Boolean spoilerAlert=false;
    public Date date=Date.from(Instant.now());

    @NotNull(message = "Language Id must be valid")
    public Integer languageId;
    public ReviewType type=ReviewType.NORMAL;

    //Hasta aca es igual a ReviewDTO salvo que tiene un id

    @NotNull(message = "Critic must be provided")
    public CriticDTO critic;

    public ReviewPremium toModel(Language language, Critic critic){
        return new ReviewPremium(titleId, critic, text, textExtended, rating, spoilerAlert,language);
    }

}
