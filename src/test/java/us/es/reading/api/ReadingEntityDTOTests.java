package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ReadingEntityDTOTests {
    
    // Return null when userId has not been initialized
    @Test
    public void test_user_id_returns_null_when_not_initialized() {
        ReadingEntityDTO readingEntityDTO = new ReadingEntityDTO();
    
        String result = readingEntityDTO.getUserId();
    
        assertNull(result);
    }

    // Set valid userId string and verify it is stored correctly
    @Test
    public void test_set_valid_user_id() {
        ReadingEntityDTO readingEntityDTO = new ReadingEntityDTO();
        String expectedUserId = "user123";
    
        readingEntityDTO.setUserId(expectedUserId);
    
        assertEquals(expectedUserId, readingEntityDTO.getUserId());
    }

    // Set null userId
    @Test
    public void test_set_null_user_id() {
        ReadingEntityDTO readingEntityDTO = new ReadingEntityDTO();
    
        readingEntityDTO.setUserId(null);
    
        assertNull(readingEntityDTO.getUserId());
    }

    // Return list of GenreDTO objects when genres field is initialized
    @Test
    public void test_returns_genre_list_when_initialized() {
        List<GenreDTO> expectedGenres = Arrays.asList(
            new GenreDTO(),
            new GenreDTO()
        );
    
        ReadingEntityDTO readingEntity = new ReadingEntityDTO();
        readingEntity.setGenres(expectedGenres);
    
        List<GenreDTO> actualGenres = readingEntity.getGenres();
    
        assertEquals(expectedGenres, actualGenres);
        assertEquals(2, actualGenres.size());
    }

    // Return unmodifiable list to prevent external modifications
    @Test
    public void test_returns_unmodifiable_list() {
        List<GenreDTO> initialGenres = Arrays.asList(
            new GenreDTO()
        );
    
        ReadingEntityDTO readingEntity = new ReadingEntityDTO();
        readingEntity.setGenres(initialGenres);
    
        List<GenreDTO> returnedGenres = readingEntity.getGenres();
    
        assertThrows(UnsupportedOperationException.class, () -> {
            returnedGenres.add(new GenreDTO());
        });
    }

    // Set null as the genres list and verify it is handled properly
    @Test
    public void test_set_null_genres_list() {
        ReadingEntityDTO readingEntity = new ReadingEntityDTO();
        List<GenreDTO> genres = null;

        readingEntity.setGenres(genres);

        assertNull(readingEntity.getGenres());
    }
}
