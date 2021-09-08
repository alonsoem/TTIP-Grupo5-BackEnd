package ar.edu.unq.desapp.grupoj.backenddesappapi.service.dtos;

import ar.edu.unq.desapp.grupoj.backenddesappapi.model.user.Critic;

public class CriticDTO {

        private Integer sourceId;
        private String userId;
        private Integer locationId;

        protected CriticDTO(){}

        public String getUserId() {
            return userId;
        }

        public Integer getSourceId() {
            return sourceId;
        }

    public Integer getLocationId() {
        return locationId;
    }

    public CriticDTO(Integer sourceId, String userId, Integer locationId){
            this.sourceId=sourceId;
            this.userId=userId;
            this.locationId=locationId;
        }

        public static CriticDTO fromModel(Critic critic){
            return new CriticDTO(critic.getSource().getId(),critic.getUserId(),critic.getLocation().getId());
        }

}
