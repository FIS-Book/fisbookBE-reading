package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenreListDTOTests {

    // Set a non-empty list of BookDTO objects and verify it is stored correctly
    @Test
    public void test_set_books_stores_list_correctly() {
        GenreListDTO genreList = new GenreListDTO();
        List<BookDTO> books = Arrays.asList(
            new BookDTO(),
            new BookDTO()
        );

        genreList.setBooks(books);

        assertEquals(books, genreList.getBooks());
    }

    // Set null as the books list value
    @Test
    public void test_set_books_accepts_null() {
        GenreListDTO genreList = new GenreListDTO();

        genreList.setBooks(null);

        assertNull(genreList.getBooks());
    }

    // Return list with null BookDTO elements if list contains nulls
    @Test
    public void test_returns_list_with_null_elements() {
        List<BookDTO> booksWithNulls = Arrays.asList(
            new BookDTO(),
            null,
            new BookDTO()
        );
    
        GenreListDTO genreList = new GenreListDTO();
        genreList.setBooks(booksWithNulls);
    
        List<BookDTO> actualBooks = genreList.getBooks();
    
        assertEquals(booksWithNulls, actualBooks);
        assertNull(actualBooks.get(1));
        assertEquals(3, actualBooks.size());
    }
}
