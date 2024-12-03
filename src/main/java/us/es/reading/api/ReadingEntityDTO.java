package us.es.reading.api;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class ReadingEntityDTO {

    @NotBlank(message = "El userId es obligatorio.")
    private String userId;

    private List<GenreDTO> genres;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }
}
