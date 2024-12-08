package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookDTOTests {

    // Return null when ISBN has not been initialized
    @Test
    public void test_isbn_returns_null_when_not_initialized() {
        BookDTO book = new BookDTO();
    
        String result = book.getIsbn();
    
        assertNull(result);
    }

    // Set valid ISBN string and verify it is stored correctly
    @Test
    public void test_set_valid_isbn() {
        BookDTO book = new BookDTO();
        String validIsbn = "0-7475-3269-9";
    
        book.setIsbn(validIsbn);
    
        assertEquals(validIsbn, book.getIsbn());
    }

    // Set null as ISBN value
    @Test
    public void test_set_null_isbn() {
        BookDTO book = new BookDTO();
    
        book.setIsbn(null);
    
        assertNull(book.getIsbn());
    }

    // Return null when title field has not been initialized
    @Test
    public void test_title_returns_null_when_not_initialized() {
        BookDTO book = new BookDTO();
    
        String result = book.getTitle();
    
        assertNull(result);
    }

    // Set valid title string and verify it is stored correctly
    @Test
    public void test_set_valid_title() {
        BookDTO book = new BookDTO();
        String expectedTitle = "Test Book Title";
    
        book.setTitle(expectedTitle);
    
        assertEquals(expectedTitle, book.getTitle());
    }

    // Set title to null value
    @Test
    public void test_set_null_title() {
        BookDTO book = new BookDTO();
    
        book.setTitle(null);
    
        assertNull(book.getTitle());
    }

    // Return null when userId has not been initialized
    @Test
    public void test_user_id_returns_null_when_not_initialized() {
        BookDTO bookDTO = new BookDTO();
    
        String result = bookDTO.getUserId();
    
        assertNull(result);
    }

    // Set valid userId string and verify it is stored correctly
    @Test
    public void test_set_valid_user_id() {
        BookDTO bookDTO = new BookDTO();
        String expectedUserId = "user123";
    
        bookDTO.setUserId(expectedUserId);
    
        assertEquals(expectedUserId, bookDTO.getUserId());
    }

    // Set null userId
    @Test
    public void test_set_null_user_id() {
        BookDTO bookDTO = new BookDTO();
    
        bookDTO.setUserId(null);
    
        assertNull(bookDTO.getUserId());
    }

    // Return null when genre field has not been initialized
    @Test
    public void test_genre_returns_null_when_not_initialized() {
        BookDTO book = new BookDTO();
    
        String result = book.getGenre();
    
        assertNull(result);
    }

    // Set genre with valid string value and verify it is stored correctly
    @Test
    public void test_set_genre_with_valid_value() {
        BookDTO book = new BookDTO();
        String expectedGenre = "Fiction";
    
        book.setGenre(expectedGenre);
    
        assertEquals(expectedGenre, book.getGenre());
    }



    // Set null userId
    @Test
    public void test_set_null_genre() {
        BookDTO bookDTO = new BookDTO();
    
        bookDTO.setGenre(null);
    
        assertNull(bookDTO.getGenre());
    }
}
