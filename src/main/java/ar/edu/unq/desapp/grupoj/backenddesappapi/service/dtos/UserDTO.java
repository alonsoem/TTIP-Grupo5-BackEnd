package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.User;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
public class UserDTO {
    @NotNull (message = "Source ID cannot be null")
    private Integer sourceId;
    @NotBlank(message = "User ID cannot be null")
    private String userId;
    @NotBlank(message = "User Nick cannot be null")
    private String userNick;
    @NotNull (message = "Location ID cannot be null")
    private Integer locationId;

    protected UserDTO(){}

    public String getUserId() {
        return userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public UserDTO(Integer sourceId, String userId, String userNick, Integer locationId){
        this.sourceId=sourceId;
        this.userId=userId;
        this.userNick=userNick;
        this.locationId=locationId;
    }

    public static UserDTO fromModel(User user){
        return new UserDTO(user.getSource().getId(),user.getUserId(),user.getUserNick(),user.getLocation().getId());
    }


}
