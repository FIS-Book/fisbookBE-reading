package us.es.reading.api;

import java.util.List;

public class GenreListDTO extends GenreDTO {

    private List<BookDTO> books;

    public GenreListDTO() {
        super();
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

}
