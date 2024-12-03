package us.es.reading.api;

import jakarta.validation.constraints.NotBlank;

public class GenreDTO {

    @NotBlank(message = "El userId es obligatorio.")
    private String userId;

    @NotBlank(message = "El gender es obligatorio.")
    private String genre;

    @NotBlank(message = "El title es obligatorio.")
    private String title;

    @NotBlank(message = "La description es obligatoria.")
    private String description;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
