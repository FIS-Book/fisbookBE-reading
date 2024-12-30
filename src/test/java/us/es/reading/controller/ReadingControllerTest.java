package us.es.reading.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import us.es.reading.api.BookDTO;
import us.es.reading.api.GenreDTO;
import us.es.reading.entity.ReadingEntity;
import us.es.reading.exception.ConflictoException;
import us.es.reading.exception.PreconditionException;
import us.es.reading.exception.ResourceNotFoundException;
import us.es.reading.service.impl.ReadingService;

public class ReadingControllerTest {

    @Mock
    private ReadingService readingService;

    @InjectMocks
    private ReadingController controller;

    private ReadingEntity readingEntity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        readingEntity = new ReadingEntity();
    }

    // GET request returns 200 with reading list when valid userId is provided
    @Test
    public void test_get_readings_returns_200_with_reading_list() {
        String userId = "002";

        when(readingService.getReadingsByUserId(userId)).thenReturn(readingEntity);

        ResponseEntity<ReadingEntity> response = controller.getReadingsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(readingEntity, response.getBody());
        verify(readingService).getReadingsByUserId(userId);
    }

    // GET request returns 404 when userId does not exist
    @Test
    public void test_get_readings_returns_404_when_user_not_found() {
        String userId = "001";

        when(readingService.getReadingsByUserId(userId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThrows(ResponseStatusException.class, () -> {
            controller.getReadingsByUserId(userId);
        });

        verify(readingService).getReadingsByUserId(userId);
    }

    // POST request creates new reading list and returns 201 status
    @Test
    public void test_create_reading_returns_201() {
        String userId = "002";

        when(readingService.createReadingEntity(userId)).thenReturn(readingEntity);

        ReadingEntity response = controller.createReading(userId);

        assertEquals(readingEntity, response);
    }

    // PUT request adds new genre and returns updated reading entity
    @Test
    public void test_add_genre_returns_updated_entity() {
        GenreDTO dto = new GenreDTO();
        when(readingService.addGenreToReadingEntity(dto)).thenReturn(readingEntity);

        ReadingEntity response = controller.createNewGenrerReading(dto);

        assertEquals(readingEntity, response);
    }

    // PUT request adds new book to genre and returns updated reading entity
    @Test
    public void test_add_book_returns_updated_entity() {
        BookDTO dto = new BookDTO();

        when(readingService.addBookToGenre(dto)).thenReturn(readingEntity);

        ReadingEntity response = controller.addBookToGenre(dto);

        assertEquals(readingEntity, response);
    }

    // DELETE request removes genre and returns success message
    @Test
    public void test_delete_genre_returns_success_message() {
        String userId = "002";
        String genre = "fiction";
        String expectedMessage = "Genre deleted successfully";
        when(readingService.deleteGenre(userId, genre)).thenReturn(expectedMessage);

        ResponseEntity<String> response = controller.deleteGenre(userId, genre);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    // DELETE request removes book and returns updated reading entity
    @Test
    public void test_remove_book_returns_updated_entity() {
        String userId = "user123";
        String genre = "fiction";
        String isbn = "123456";

        when(readingService.removeBook(userId, genre, isbn)).thenReturn(readingEntity);

        ResponseEntity<ReadingEntity> response = controller.removeBook(userId, genre, isbn);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(readingEntity, response.getBody());
    }

    // GET request with non-existent userId returns 404
    @Test
    public void test_get_readings_returns_404_for_invalid_user() {
        String userId = "006";
        when(readingService.getReadingsByUserId(userId)).thenThrow(new ResourceNotFoundException("User not found"));

        assertThrows(ResourceNotFoundException.class, () -> controller.getReadingsByUserId(userId));
    }

    // POST request with invalid userId format returns 412
    @Test
    public void test_create_reading_returns_412_for_invalid_format() {
        String invalidUserId = "";
        when(readingService.createReadingEntity(invalidUserId))
                .thenThrow(new PreconditionException("Invalid userId format"));

        assertThrows(PreconditionException.class, () -> controller.createReading(invalidUserId));
    }

    // PUT request for genre with duplicate data returns 409
    @Test
    public void test_add_genre_returns_409_for_duplicate() {
        GenreDTO dto = new GenreDTO();
        when(readingService.addGenreToReadingEntity(dto)).thenThrow(new ConflictoException("Genre already exists"));

        assertThrows(ConflictoException.class, () -> controller.createNewGenrerReading(dto));
    }

    // PUT request for book with invalid genre returns 412
    @Test
    public void test_add_book_returns_412_for_invalid_genre() {
        BookDTO dto = new BookDTO();
        when(readingService.addBookToGenre(dto)).thenThrow(new PreconditionException("Invalid genre"));

        assertThrows(PreconditionException.class, () -> controller.addBookToGenre(dto));
    }

    // DELETE request for non-existent genre returns 404
    @Test
    public void test_delete_genre_returns_404_for_nonexistent() {
        String userId = "002";
        String genre = "nonexistent";
        when(readingService.deleteGenre(userId, genre)).thenThrow(new ResourceNotFoundException("Genre not found"));

        assertThrows(ResourceNotFoundException.class, () -> controller.deleteGenre(userId, genre));
    }

    // DELETE request for non-existent book returns 404
    @Test
    public void test_remove_book_returns_404_for_nonexistent() {
        String userId = "002";
        String genre = "fiction";
        String isbn = "nonexistent";
        when(readingService.removeBook(userId, genre, isbn)).thenThrow(new ResourceNotFoundException("Book not found"));

        assertThrows(ResourceNotFoundException.class, () -> controller.removeBook(userId, genre, isbn));
    }
}
