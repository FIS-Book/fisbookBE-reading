package us.es.reading.entity;

import java.util.List;

public class Genre {

    private String genre;

    private String title;

    private String description;

    private List<Book> books;

    private String genreId;

    private Integer numberReviews;

    private Double score;

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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

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
