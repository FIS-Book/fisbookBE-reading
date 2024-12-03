package us.es.reading.api;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {

    @NotBlank(message = "El userId es obligatorio.")
    private String userId;

    @NotBlank(message = "El userId es obligatorio.")
    private String genre;

    @NotBlank(message = "El isbn es obligatorio.")
    private String isbn;

    @NotBlank(message = "El title es obligatorio.")
    private String title;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
}
