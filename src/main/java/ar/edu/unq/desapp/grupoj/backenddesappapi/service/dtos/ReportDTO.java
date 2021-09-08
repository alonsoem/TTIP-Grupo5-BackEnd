package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.Reason;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
public class ReportDTO {

    @NotNull(message = "Review ID must be provided")
    public Integer reviewId;
    @NotNull(message = "Reason must be provided")
    public Reason reason;
    @NotBlank(message = "Description cannot be blank")
    public String description;
    @NotNull(message = "User must be provided")
    public UserDTO user;

    protected ReportDTO(){}

    public ReportDTO(Integer reviewId, Reason reason, String description, UserDTO user){
        this.description=description;
        this.reason=reason;
        this.reviewId=reviewId;
        this.user=user;
    }


}
