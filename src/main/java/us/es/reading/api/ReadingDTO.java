package us.es.reading.api;

import jakarta.validation.constraints.NotBlank;

public class ReadingDTO {

    @NotBlank(message = "El userId es obligatorio.")
    private String userId;
    
    @NotBlank(message = "El userKey es obligatorio.")
    private String userKey;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }   

}
