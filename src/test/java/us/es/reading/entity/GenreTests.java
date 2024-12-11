package us.es.reading.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GenreTests {
    // Return null when genre field has not been initialized
    @Test
    public void test_genre_returns_null_when_not_initialized() {
        Genre book = new Genre();
    
        String result = book.getGenre();
    
        assertNull(result);
    }

    // Set genre with valid string value and verify it is stored correctly
    @Test
    public void test_set_genre_with_valid_value() {
        Genre book = new Genre();
        String expectedGenre = "Fiction";
    
        book.setGenre(expectedGenre);
    
        assertEquals(expectedGenre, book.getGenre());
    }



    // Set null genre
    @Test
    public void test_set_null_genre() {
        Genre genre = new Genre();
    
        genre.setGenre(null);
    
        assertNull(genre.getGenre());
    }

    // Return null when title field has not been initialized
    @Test
    public void test_title_returns_null_when_not_initialized() {
        Genre book = new Genre();
    
        String result = book.getTitle();
    
        assertNull(result);
    }

    // Set valid title string and verify it is stored correctly
    @Test
    public void test_set_valid_title() {
        Genre book = new Genre();
        String expectedTitle = "Test Book Title";
    
        book.setTitle(expectedTitle);
    
        assertEquals(expectedTitle, book.getTitle());
    }

    // Set title to null value
    @Test
    public void test_set_null_title() {
        Genre book = new Genre();
    
        book.setTitle(null);
    
        assertNull(book.getTitle());
    }

    // Set description with valid non-empty string value
    @Test
    public void test_set_valid_description() {
        Genre genre = new Genre();
        String validDescription = "Fiction Genre";
    
        genre.setDescription(validDescription);
    
        assertEquals(validDescription, genre.getDescription());
    }

    // Set description with null value
    @Test
    public void test_set_null_description() {
        Genre genre = new Genre();
    
        genre.setDescription(null);
    
        assertNull(genre.getDescription());
    }

    // Return very long description string (>1000 chars)
    @Test
    public void test_get_long_description() {
        String longDescription = "a".repeat(1500);
        Genre genre = new Genre();
        genre.setDescription(longDescription);
    
        String actualDescription = genre.getDescription();
    
        assertEquals(longDescription, actualDescription);
        assertTrue(actualDescription.length() > 1000);
    }

    // Set a non-empty list of books and verify it is stored correctly
    @Test
    public void test_set_books_stores_list_correctly() {
        Genre genre = new Genre();
        List<Book> books = Arrays.asList(
            new Book(),
            new Book()
        );

        genre.setBooks(books);

        assertEquals(books, genre.getBooks());
    }

    // Set null as the books list value
    @Test
    public void test_set_books_accepts_null() {
        Genre genre = new Genre();
    
        genre.setBooks(null);
    
        assertNull(genre.getBooks());
    }

    @Test
    public void test_get_books_returns_null_when_not_initialized() {
        Genre genre = new Genre();
    
        List<Book> actualBooks = genre.getBooks();
    
        assertNull(actualBooks);
    }
}
