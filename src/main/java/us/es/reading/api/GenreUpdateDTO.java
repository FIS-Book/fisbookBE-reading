package us.es.reading.api;

import jakarta.validation.constraints.NotBlank;

public class GenreUpdateDTO {

    @NotBlank(message = "El genreId es obligatorio.")
    private String genreId;
    
    private Integer numberReviews;

    private Double score;

    public String getGenreId() {
        return genreId;
    }

    public void setGenreId(String genreId) {
        this.genreId = genreId;
    }

    public Integer getNumberReviews() {
        return numberReviews;
    }

    public void setNumberReviews(Integer numberReviews) {
        this.numberReviews = numberReviews;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
