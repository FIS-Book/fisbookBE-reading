package us.es.reading.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import us.es.reading.api.BookDTO;
import us.es.reading.api.GenreDTO;
import us.es.reading.api.GenreUpdateDTO;
import us.es.reading.exception.PreconditionException;

public class FieldsValidate {

    public static void validateFieldsToAddGenreToReadingEntity(GenreDTO dto) {
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
    }

    public static void validateFieldsToAddBookToGenre(BookDTO dto) {
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
    }

    public static void validateFieldsToRemoveBook(String userId, String genre, String isbn) {
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
    }

    public static void validateGenreUpdateDTO(GenreUpdateDTO dto) {
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
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void validateFieldsToSendEmail(String from, String to, String subject, String body, String emailKey) {
        if (from == null || from.isEmpty()) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " El origen es obligatorio.");
        }
        if (to == null || to.isEmpty()) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " El destino es obligatorio.");
        }
        if (subject == null || subject.isEmpty()) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " El asunto es obligatorio.");
        }
        if (body == null || body.isEmpty()) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " El cuerpo es obligatorio.");
        }
        if (emailKey == null || emailKey.isEmpty()
                || !emailKey.equals(Constants.KEY)) {
            throw new PreconditionException(HttpStatus.PRECONDITION_FAILED + " Clave para enviar el email incorrecta.");
        }
        if (!isValidEmail(to) || !isValidEmail(from)) {
            throw new PreconditionException(
                    HttpStatus.PRECONDITION_FAILED + " Formato de email no válido.");
        }
    }
}
