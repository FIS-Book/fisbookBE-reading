package us.es.reading.api;

import jakarta.validation.constraints.NotBlank;

public class ReadingDTO {

    @NotBlank(message = "El userId es obligatorio.")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
