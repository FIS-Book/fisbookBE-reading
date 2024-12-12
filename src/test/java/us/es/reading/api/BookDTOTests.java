package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDTOTests {

    private BookDTO dto;

    @BeforeEach
    public void setUp() {
        dto = new BookDTO();
        dto.setGenre("COMEDIA");
        dto.setIsbn("1234567890");
        dto.setTitle("Libro de comedia");
        dto.setUserId("001");
    }

    // Return null when ISBN has not been initialized
    @Test
    public void test_properties_returns_null_when_not_initialized() {
        dto = new BookDTO();
        assertNull(dto.getGenre());
        assertNull(dto.getIsbn());
        assertNull(dto.getTitle());
        assertNull(dto.getUserId());
    }

    @Test
    public void test_properties_returns_true_when_is_initialized() {

        assertNotNull(dto.getGenre());
        assertNotNull(dto.getIsbn());
        assertNotNull(dto.getTitle());
        assertNotNull(dto.getUserId());
    }

    @Test
    public void test_valid_returns_true_when_is_initialized() {
        assertEquals("COMEDIA", dto.getGenre());
        assertEquals("1234567890", dto.getIsbn());
        assertEquals("Libro de comedia", dto.getTitle());
        assertEquals("001", dto.getUserId());
    }

    @Test
    public void test_valid_returns_false_when_is_initialized() {
        assertFalse("COMEDIAs".equalsIgnoreCase(dto.getGenre()));
        assertFalse("12345678901".equalsIgnoreCase(dto.getIsbn()));
        assertFalse("Libro de comedias".equalsIgnoreCase(dto.getTitle()));
        assertFalse("002".equalsIgnoreCase(dto.getUserId()));
    }
}
