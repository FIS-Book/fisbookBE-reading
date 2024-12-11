package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenreDTOTests {

    // Returns null when userId field is not initialized
    @Test
    public void test_get_user_id_returns_null_when_not_initialized() {
        GenreDTO genreDTO = new GenreDTO();
    
        String userId = genreDTO.getUserId();
    
        assertNull(userId);
    }

    // Set valid userId string and verify it is stored correctly
    @Test
    public void test_set_valid_user_id() {
        String expectedUserId = "user123";
    
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setUserId(expectedUserId);
    
        assertEquals(expectedUserId, genreDTO.getUserId());
    }

    // Set null and verify stored value
    @Test
    public void test_set_null_user_id() {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setUserId(null);

        assertNull(genreDTO.getUserId());
    }

    // Return null when genre field has not been initialized
    @Test
    public void test_genre_returns_null_when_not_initialized() {
        GenreDTO book = new GenreDTO();
    
        String result = book.getGenre();
    
        assertNull(result);
    }

    // Set genre with valid string value and verify it is stored correctly
    @Test
    public void test_set_genre_with_valid_value() {
        GenreDTO book = new GenreDTO();
        String expectedGenre = "Fiction";
    
        book.setGenre(expectedGenre);
    
        assertEquals(expectedGenre, book.getGenre());
    }



    // Set null userId
    @Test
    public void test_set_null_genre() {
        GenreDTO genreDTO = new GenreDTO();
    
        genreDTO.setGenre(null);
    
        assertNull(genreDTO.getGenre());
    }

    // Return null when title field has not been initialized
    @Test
    public void test_title_returns_null_when_not_initialized() {
        GenreDTO book = new GenreDTO();
    
        String result = book.getTitle();
    
        assertNull(result);
    }

    // Set valid title string and verify it is stored correctly
    @Test
    public void test_set_valid_title() {
        GenreDTO book = new GenreDTO();
        String expectedTitle = "Test Book Title";
    
        book.setTitle(expectedTitle);
    
        assertEquals(expectedTitle, book.getTitle());
    }

    // Set title to null value
    @Test
    public void test_set_null_title() {
        GenreDTO book = new GenreDTO();
    
        book.setTitle(null);
    
        assertNull(book.getTitle());
    }

    // Set description with valid non-empty string value
    @Test
    public void test_set_valid_description() {
        GenreDTO genreDTO = new GenreDTO();
        String validDescription = "Fiction Genre";
    
        genreDTO.setDescription(validDescription);
    
        assertEquals(validDescription, genreDTO.getDescription());
    }

    // Set description with null value
    @Test
    public void test_set_null_description() {
        GenreDTO genreDTO = new GenreDTO();
    
        genreDTO.setDescription(null);
    
        assertNull(genreDTO.getDescription());
    }

    // Return very long description string (>1000 chars)
    @Test
    public void test_get_long_description() {
        String longDescription = "a".repeat(1500);
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setDescription(longDescription);
    
        String actualDescription = genreDTO.getDescription();
    
        assertEquals(longDescription, actualDescription);
        assertTrue(actualDescription.length() > 1000);
    }
}
