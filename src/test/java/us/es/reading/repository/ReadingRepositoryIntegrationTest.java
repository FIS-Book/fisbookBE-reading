package us.es.reading.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;

import us.es.reading.entity.Book;
import us.es.reading.entity.Genre;
import us.es.reading.entity.ReadingEntity;

@DirtiesContext
@DataMongoTest
public class ReadingRepositoryIntegrationTest {

    @Autowired
    private ReadingRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private ReadingEntity entity;

    @BeforeEach
    public void setUp() {
        entity = new ReadingEntity();
        entity.setUserId("002");

        Book book = new Book();
        book.setIsbn("123456");
        book.setTitle("La risa no tiene precio");
        List<Book> books = new ArrayList<>();
        books.add(book);

        Genre genre = new Genre();
        genre.setGenre("COMEDIA");
        genre.setTitle("Libros de comedia");
        genre.setDescription("Libros para no parar de reir");

        genre.setBooks(books);
        List<Genre> genres = new ArrayList<>();
        genres.add(genre);

        entity.setGenres(genres);

        repository.save(entity);
    }

    @AfterEach
    public void setDown() {
        mongoTemplate.getDb().drop();
    }

    @Test
    public void test_find_reading_by_userId() {
        String userId = "002";

        Optional<ReadingEntity> found = repository.findByUserId(userId);

        assertThat(found).isPresent();
        assertThat(found.get().equals(entity));
    }

    @Test
    public void test_not_found_reading_by_userId() {
        String userId = "003";

        Optional<ReadingEntity> found = repository.findByUserId(userId);

        assertThat(found).isNotPresent();
    }

    @Test
    public void test_add_genre_to_reading() {
        String userId = "002";

        Genre genre = new Genre();
        genre.setGenre("CIENCIA FICCIÓN");
        genre.setTitle("Libros de ciencia ficción");
        genre.setDescription("Libros para volverse locos imagianndo cosas");

        Optional<ReadingEntity> found = repository.findByUserId(userId);

        ReadingEntity entity = found.get();
        List<Genre> genres = entity.getGenres();
        genres.add(genre);
        entity.setGenres(genres);

        repository.save(entity);

        found = repository.findByUserId(userId);

        assertThat(found).isPresent();

        assertThat(found.get().getGenres().size() == 2);
    }

    @Test
    public void test_add_bookto_genre_to_reading() {
        String userId = "002";

        Book book1 = new Book();
        book1.setIsbn("123457");
        book1.setTitle("Muerte por risa");

        Book book2 = new Book();
        book2.setIsbn("123458");
        book2.setTitle("Que se muera la tristeza");

        Optional<ReadingEntity> found = repository.findByUserId(userId);

        ReadingEntity entity = found.get();
        List<Genre> genres = entity.getGenres();
        Genre genre = genres.get(0);
        List<Book> books = genre.getBooks();
        books.add(book1);
        books.add(book2);
        genre.setBooks(books);
        entity.setGenres(genres);

        repository.save(entity);

        found = repository.findByUserId(userId);

        assertThat(found).isPresent();
        assertThat(found.get().getGenres().size() == 1);
        assertThat(found.get().getGenres().get(0).getBooks().size() == 3);
    }

    @Test
    public void test_delete_genre() {
        String userId = "002";

        Optional<ReadingEntity> found = repository.findByUserId(userId);

        ReadingEntity entity = found.get();
        entity.setGenres(null);

        repository.save(entity);

        found = repository.findByUserId(userId);

        assertThat(found).isPresent();
        assertThat(found.get().getGenres() == null);
    }

    @Test
    public void test_delete_book() {
        String userId = "002";

        Optional<ReadingEntity> found = repository.findByUserId(userId);

        ReadingEntity entity = found.get();
        List<Genre> genres = entity.getGenres();
        for (Genre genre : genres) {
            if (genre.getGenre().equalsIgnoreCase("COMEDIA")) {
                List<Book> books = genres.get(0).getBooks();
                for (Book book : books) {
                    if (book.getIsbn().equals("123456")) {
                        books.remove(book);
                        break;
                    }
                }
                genre.setBooks(books);
                break;
            }
        }
        entity.setGenres(genres);

        repository.save(entity);

        found = repository.findByUserId(userId);
        assertThat(found).isPresent();
        assertThat(found.get().getGenres().get(0).getBooks().size() == 0);
    }
}
