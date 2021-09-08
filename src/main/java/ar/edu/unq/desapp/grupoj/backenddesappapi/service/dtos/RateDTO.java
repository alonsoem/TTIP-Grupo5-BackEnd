package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.RateType;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public class RateDTO {

    @NotNull (message = "User must be provided")
    public UserDTO user;
    @NotNull (message = "Review ID must be provided")
    public Integer reviewId;
    @NotNull (message = "Rate Type must be provided")
    public RateType rateType;

    public RateDTO(UserDTO user, Integer reviewId,RateType rateType){
        this.user=user;
        this.reviewId=reviewId;
        this.rateType=rateType;
    }
    protected  RateDTO(){}

}
