package us.es.reading.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class ReadingEntityTests {

    // Return null when id field has not been initialized
    @Test
    public void test_id_returns_null_when_not_initialized() {
        ReadingEntity entity = new ReadingEntity();
    
        ObjectId result = entity.getId();
    
        assertNull(result);
    }

    // Set valid ObjectId and verify it is stored correctly
    @Test
    public void test_set_valid_object_id() {
        ReadingEntity entity = new ReadingEntity();
        ObjectId expectedId = new ObjectId();

        entity.setId(expectedId);

        assertEquals(expectedId, entity.getId());
    }

    // Return null after id is set to null
    @Test
    public void test_get_id_returns_null() {
        ReadingEntity entity = new ReadingEntity();
        entity.setId(null);

        ObjectId actualId = entity.getId();

        assertNull(actualId);
    }

    // Return null when userId has not been initialized
    @Test
    public void test_user_id_returns_null_when_not_initialized() {
        ReadingEntity readingEntity = new ReadingEntity();
    
        String result = readingEntity.getUserId();
    
        assertNull(result);
    }

    // Set valid userId string and verify it is stored correctly
    @Test
    public void test_set_valid_user_id() {
        ReadingEntity readingEntity = new ReadingEntity();
        String expectedUserId = "user123";
    
        readingEntity.setUserId(expectedUserId);
    
        assertEquals(expectedUserId, readingEntity.getUserId());
    }

    // Set null userId
    @Test
    public void test_set_null_user_id() {
        ReadingEntity readingEntity = new ReadingEntity();
    
        readingEntity.setUserId(null);
    
        assertNull(readingEntity.getUserId());
    }

    // Set a non-empty list of genres and verify it is stored correctly
    @Test
    public void test_set_non_empty_genres_list() {
        ReadingEntity readingEntity = new ReadingEntity();
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        List<Genre> genres = Arrays.asList(genre1, genre2);
    
        readingEntity.setGenres(genres);
    
        assertEquals(genres, readingEntity.getGenres());
    }

    // Set null as the genres list value
    @Test
    public void test_set_null_genres_list() {
        ReadingEntity readingEntity = new ReadingEntity();
    
        readingEntity.setGenres(null);
    
        assertNull(readingEntity.getGenres());
    }

    // Return list with null elements if genres list contains null values
    @Test
    public void test_get_genres_allows_null_elements() {
        Genre genre1 = new Genre();
        Genre genre2 = new Genre();
        List<Genre> genresWithNull = Arrays.asList(genre1, null, genre2);
    
        ReadingEntity readingEntity = new ReadingEntity();
        readingEntity.setGenres(genresWithNull);
    
        List<Genre> actualGenres = readingEntity.getGenres();
    
        assertNotNull(actualGenres);
        assertEquals(3, actualGenres.size());
        assertNull(actualGenres.get(1));
    }
}
