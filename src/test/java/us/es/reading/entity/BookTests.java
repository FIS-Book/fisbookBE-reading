package us.es.reading.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookTests {

    // Return null when ISBN has not been initialized
    @Test
    public void test_isbn_returns_null_when_not_initialized() {
        Book book = new Book();
    
        String result = book.getIsbn();
    
        assertNull(result);
    }

    // Set valid ISBN string and verify it is stored correctly
    @Test
    public void test_set_valid_isbn() {
        Book book = new Book();
        String validIsbn = "0-7475-3269-9";
    
        book.setIsbn(validIsbn);
    
        assertEquals(validIsbn, book.getIsbn());
    }

    // Set null as ISBN value
    @Test
    public void test_set_null_isbn() {
        Book book = new Book();
    
        book.setIsbn(null);
    
        assertNull(book.getIsbn());
    }

    // Return null when title field has not been initialized
    @Test
    public void test_title_returns_null_when_not_initialized() {
        Book book = new Book();
    
        String result = book.getTitle();
    
        assertNull(result);
    }

    // Set valid title string and verify it is stored correctly
    @Test
    public void test_set_valid_title() {
        Book book = new Book();
        String expectedTitle = "Test Book Title";
    
        book.setTitle(expectedTitle);
    
        assertEquals(expectedTitle, book.getTitle());
    }

    // Set title to null value
    @Test
    public void test_set_null_title() {
        Book book = new Book();
    
        book.setTitle(null);
    
        assertNull(book.getTitle());
    }
}
