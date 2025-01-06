package us.es.reading.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import us.es.reading.api.BookDTO;
import us.es.reading.api.GenreDTO;
import us.es.reading.api.GenreUpdateDTO;
import us.es.reading.entity.Book;
import us.es.reading.entity.Genre;
import us.es.reading.entity.ReadingEntity;
import us.es.reading.exception.ConflictoException;
import us.es.reading.exception.PreconditionException;
import us.es.reading.exception.ResourceNotFoundException;
import us.es.reading.repository.ReadingRepository;
import us.es.reading.template.BookService;
import us.es.reading.utils.FieldsValidate;

@Service
public class ReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    @Autowired
    private BookService bookService;

    public ReadingEntity createReadingEntity(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El id del usuario es obligatorio.");
        }
        ReadingEntity readingEntity = null;
        try {
            readingEntity = readingRepository.findByUserId(userId).get();
        } catch (Exception e) {
            e.getMessage();
        }
        if (Objects.nonNull(readingEntity)) {
            throw new ConflictoException("El usuario " + userId + " ya existe.");
        }
        readingEntity = new ReadingEntity();
        readingEntity.setUserId(userId);
        readingEntity.setGenres(new ArrayList<>());
        return readingRepository.save(readingEntity);
    }

    public ReadingEntity addGenreToReadingEntity(GenreDTO dto) {
        FieldsValidate.validateFieldsToAddGenreToReadingEntity(dto);
        ReadingEntity readingEntity = readingRepository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lista de lecturas con ID " + dto.getUserId() + " no encontrado."));
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenre().equalsIgnoreCase(dto.getGenre())).findFirst();
        if (!genreOpt.isEmpty()) {
            throw new ConflictoException(
                    HttpStatus.CONFLICT + " El género " + dto.getGenre() + " para este usuario ya existe");
        }
        int size = readingEntity.getGenres().size();
        Genre genre = generateGenre(dto, size);
        readingEntity.getGenres().add(genre);
        return readingRepository.save(readingEntity);
    }

    private Genre generateGenre(GenreDTO dto, int size) {
        Genre genre = new Genre();
        genre.setGenre(dto.getGenre());
        genre.setTitle(dto.getTitle());
        genre.setDescription(dto.getDescription());
        genre.setBooks(new ArrayList<>());
        genre.setGenreId(dto.getUserId() + "_" + size);
        genre.setNumberReviews(0);
        genre.setScore(0.0);
        return genre;
    }

    public ReadingEntity addBookToGenre(BookDTO dto) {
        FieldsValidate.validateFieldsToAddBookToGenre(dto);
        ReadingEntity readingEntity = readingRepository.findByUserId(dto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("Lista de lecturas con ID " + dto.getUserId() + " no encontrado."));
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenre().equalsIgnoreCase(dto.getGenre())).findFirst();

        if (genreOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                    "El género " + dto.getGenre() + " no fue encontrado. Debe crear el género");
        }
        Genre genre = genreOpt.get();
        Optional<Book> existingBook = genre.getBooks().stream()
                .filter(b -> b.getIsbn().equals(dto.getIsbn()))
                .findFirst();
        if (existingBook.isPresent()) {
            throw new ConflictoException("El libro con ISBN " + dto.getIsbn() + " ya existe en este género.");
        }
        Book book = new Book();
        book.setIsbn(dto.getIsbn());
        book.setTitle(dto.getTitle());
        genre.getBooks().add(book);
        ReadingEntity result = readingRepository.save(readingEntity);

        deleteAndUpdateBookWithApiExternal(dto.getIsbn());

        return result;
    }

    public ReadingEntity removeBook(String userId, String genre, String isbn) {
        FieldsValidate.validateFieldsToRemoveBook(userId, genre, isbn);
        ReadingEntity readingEntity = readingRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Lista de lecturas con ID " + userId + " no encontrado."));
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenre().equalsIgnoreCase(genre))
                .findFirst();
        if (genreOpt.isEmpty()) {
            throw new ResourceNotFoundException("El género " + genre + " no fue encontrado.");
        }
        Genre genreDto = genreOpt.get();
        Optional<Book> bookOpt = genreDto.getBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();
        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("El libro con ISBN " + isbn + " no existe en el género " + genre);
        }
        genreDto.getBooks().removeIf(b -> b.getIsbn().equals(isbn));

        ReadingEntity result = readingRepository.save(readingEntity);

        deleteAndUpdateBookWithApiExternal(isbn);

        return result;
    }

    private void deleteAndUpdateBookWithApiExternal(String isbn) {
        if (Objects.nonNull(isbn) && !isbn.isEmpty()) {
            int inReadingLists = countBooksByIsbn(isbn);
            String jwtToken = getJwtFromContext();
            bookService.booksExternalApi(isbn, inReadingLists, jwtToken);
        }
    }

    public String deleteGenre(String userId, String genre) {
        // Buscar el ReadingEntity asociado al userId
        ReadingEntity reading = readingRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe o no tiene lecturas creadas"));

        // Buscar el Genre que se quiere eliminar
        Optional<Genre> genreToRemove = reading.getGenres().stream()
                .filter(g -> Objects.nonNull(g.getGenre()) && g.getGenre().equalsIgnoreCase(genre))
                .findFirst();

        if (genreToRemove.isEmpty()) {
            throw new ResourceNotFoundException("Genero " + genre + " no encontrado");
        }

        // Llamar a deleteAndUpdateBookWithApiExternal por cada libro del Genre
        genreToRemove.get().getBooks().forEach(book -> removeBook(userId, genre, book.getIsbn()));

        // Eliminar el Genre de la lista de géneros
        boolean removed = reading.getGenres()
                .removeIf(g -> Objects.nonNull(g.getGenre()) && g.getGenre().equalsIgnoreCase(genre));
        if (!removed) {
            throw new ResourceNotFoundException("Genero " + genre + " no encontrado");
        }

        // Guardar la entidad actualizada
        readingRepository.save(reading);

        return "La lista de género " + genre + " ha sido eliminada correctamente.";
    }

    public ReadingEntity getReadingsByUserId(String userId) {
        return readingRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe o no tiene lecturas creadas"));
    }

    public Genre getGenreByGenreId(String genreId) {
        String userId = genreId.split("_")[0];
        ReadingEntity readingEntity = readingRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Lista de lecturas con ID " + userId + " no encontrado."));
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenreId().equalsIgnoreCase(genreId)).findFirst();
        return genreOpt.get();
    }

    public Genre updateGenre(GenreUpdateDTO dto) {
        FieldsValidate.validateGenreUpdateDTO(dto);
        String userId = dto.getGenreId().split("_")[0];

        ReadingEntity readingEntity = readingRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lista de lecturas con ID " + dto.getGenreId() + " no encontrado."));
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenreId().equalsIgnoreCase(dto.getGenreId())).findFirst();

        if (!genreOpt.isPresent()) {
            throw new PreconditionException(
                    HttpStatus.NOT_FOUND + " Lista de lecuras con id " + dto.getGenreId() + " no encontradas");
        }

        Genre genre = genreOpt.get();
        if (Objects.nonNull(dto.getNumberReviews())) {
            genre.setNumberReviews(dto.getNumberReviews());
        }
        if (Objects.nonNull(dto.getScore())) {
            genre.setScore(dto.getScore());
        }
        readingRepository.save(readingEntity);
        return genre;
    }

    private String getJwtFromContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalStateException("JWT no encontrado en el encabezado Authorization");
    }

    private Integer countBooksByIsbn(String isbn) {
        List<ReadingEntity> allReadings = readingRepository.findAll();
        long count = allReadings.stream()
                .flatMap(reading -> reading.getGenres().stream()) // Obtener todos los géneros
                .flatMap(genre -> genre.getBooks().stream()) // Obtener todos los libros de cada género
                .filter(book -> book.getIsbn().equals(isbn)) // Filtrar libros con el ISBN especificado
                .count(); // Contar los resultados
        if (count > Integer.MAX_VALUE) {
            throw new ArithmeticException("El conteo de libros excede el límite de un Integer.");
        }
        return (int) count;
    }
}
