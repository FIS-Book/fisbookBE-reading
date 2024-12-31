package us.es.reading.service.impl;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import us.es.reading.utils.ISBNValidator;

@Service
public class ReadingService {

    @Autowired
    private ReadingRepository readingRepository;

    public ReadingEntity createReadingEntity(String userId) {
        // Validar que todos los campos obligatorios están presentes
        if (userId == null || userId.isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El id del usuario es obligatorio.");
        }
        ReadingEntity readingEntity = null;
        try {
            readingEntity = readingRepository.findByUserId(userId).get();
        } catch (Exception e) {
            System.out.println(" = >" + e.getMessage());
        }

        if (Objects.nonNull(readingEntity)) {
            throw new ConflictoException("El usuario " + userId + " ya existe.");
        }
        // Crear la entidad de lectura
        readingEntity = new ReadingEntity();
        readingEntity.setUserId(userId);
        readingEntity.setGenres(new ArrayList<>());
        // Guardar la entidad en la base de datos
        return readingRepository.save(readingEntity);
    }

    public ReadingEntity addGenreToReadingEntity(GenreDTO dto) {
        // Validar que todos los campos obligatorios están presentes
        if (dto.getGenre() == null || dto.getGenre().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El genero es obligatorio.");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El título es obligatorio.");
        }
        if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " La descripción es obligatoria.");
        }

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

        Genre genre = new Genre();
        genre.setGenre(dto.getGenre());
        genre.setTitle(dto.getTitle());
        genre.setDescription(dto.getDescription());
        genre.setBooks(new ArrayList<>());
        genre.setGenreId(dto.getUserId() + "_" + size);
        genre.setNumberReviews(0);
        genre.setScore(0.0);
        readingEntity.getGenres().add(genre);
        return readingRepository.save(readingEntity);
    }

    public ReadingEntity addBookToGenre(BookDTO dto) {
        // Validar que todos los campos obligatorios están presentes
        if (dto.getUserId() == null || dto.getUserId().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El id del usuario es obligatorio.");
        }
        if (dto.getGenre() == null || dto.getGenre().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El genero es obligatorio.");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El título es obligatorio.");
        }
        if (dto.getIsbn() == null || dto.getIsbn().isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El ISBN es obligatoria.");
        }
        if (!ISBNValidator.isValidISBN(dto.getIsbn())) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El ISBN no es válido.");
        }

        ReadingEntity readingEntity = readingRepository.findByUserId(dto.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("Lista de lecturas con ID " + dto.getUserId() + " no encontrado."));

        // Buscar el género en la lista de genres
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenre().equalsIgnoreCase(dto.getGenre())).findFirst();

        if (genreOpt.isEmpty()) {
            throw new ResourceNotFoundException(
                    "El género " + dto.getGenre() + " no fue encontrado. Debe crear el género");
        }

        Genre genre = genreOpt.get();
        // Verificar si el libro con el mismo ISBN ya existe en el género
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

        return readingRepository.save(readingEntity);
    }

    public ReadingEntity removeBook(String userId, String genre, String isbn) {
        // Validar que todos los campos obligatorios están presentes
        if (userId == null || userId.isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El id del usuario es obligatorio.");
        }
        if (genre == null || genre.isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El genero es obligatorio.");
        }
        if (isbn == null || isbn.isEmpty()) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El ISBN es obligatorio.");
        }
        if (!ISBNValidator.isValidISBN(isbn)) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " El ISBN no es válido.");
        }

        // Buscar la lista de lecturas por userId
        ReadingEntity readingEntity = readingRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Lista de lecturas con ID " + userId + " no encontrado."));

        // Buscar el género en la lista de géneros
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenre().equalsIgnoreCase(genre))
                .findFirst();

        if (genreOpt.isEmpty()) {
            throw new ResourceNotFoundException("El género " + genre + " no fue encontrado.");
        }

        Genre genreDto = genreOpt.get();

        // Verificar si el libro con el mismo ISBN existe en el género
        Optional<Book> bookOpt = genreDto.getBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();

        if (bookOpt.isEmpty()) {
            throw new ResourceNotFoundException("El libro con ISBN " + isbn + " no existe en el género " + genre);
        }

        // Eliminar el libro de la lista de libros en el género
        genreDto.getBooks().removeIf(b -> b.getIsbn().equals(isbn));

        // Guardar la entidad actualizada
        return readingRepository.save(readingEntity);
    }

    public String deleteGenre(String userId, String genre) {
        // Busca el documento correspondiente al userId
        ReadingEntity reading = readingRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe o no tiene lecturas creadas"));

        // Filtra los géneros para eliminar el solicitado
        boolean removed = reading.getGenres()
                .removeIf(g -> Objects.nonNull(g.getGenre()) && g.getGenre().equalsIgnoreCase(genre));

        if (!removed) {
            throw new ResourceNotFoundException("Genero " + genre + " no encontrado");
        }
        // Guarda el documento actualizado
        readingRepository.save(reading);
        return "La lista de genero " + genre + " ha sido eliminada correctamente.";
    }

    public ReadingEntity getReadingsByUserId(String userId) {
        return readingRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe o no tiene lecturas creadas"));
    }

    public Genre getGenreByGenreId(String genreId) {
        String userId = genreId.split("_")[0];

        ReadingEntity readingEntity = readingRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Lista de lecturas con ID " + userId + " no encontrado."));
        // Buscar el género en la lista de genres
        Optional<Genre> genreOpt = readingEntity.getGenres().stream()
                .filter(g -> g.getGenreId().equalsIgnoreCase(genreId)).findFirst();

        return genreOpt.get();
    }

    public Genre updateGenre(GenreUpdateDTO dto) {
        // Validar que todos los campos obligatorios están presentes
        if (dto.getGenreId() == null || dto.getGenreId().isEmpty()) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " El identificador del genero es obligatorio.");
        }
        if (!dto.getGenreId().contains("_") || dto.getGenreId().split("_").length != 2) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " Formato de identificador de genero no válido.");
        }
        if (dto.getNumberReviews() != null && dto.getNumberReviews() <= 0) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " Las reseñas no puden ser cero ni negativas.");
        }
        if (dto.getScore() != null && (dto.getScore() <= 0.0)) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " Las puntuaciones no pueden ser cero ni negativas");
        }

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
}
