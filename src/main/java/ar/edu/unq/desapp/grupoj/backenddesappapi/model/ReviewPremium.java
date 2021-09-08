package ar.edu.unq.desapp.grupoj.backenddesappapi.model;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;
import javax.persistence.Entity;

@Entity
public class ReviewPremium extends Review{

    public ReviewPremium(Integer titleId, Critic critic, String text, String textExtended, Integer rating, Boolean haveSpoiler, Language language){
        super(titleId,critic, text,textExtended,rating,haveSpoiler, language);
        super.type= ReviewType.PREMIUM;
    }

    protected ReviewPremium(){ }


}
