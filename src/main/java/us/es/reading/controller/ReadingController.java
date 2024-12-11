package us.es.reading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import us.es.reading.api.BookDTO;
import us.es.reading.api.GenreDTO;
import us.es.reading.entity.ReadingEntity;
import us.es.reading.service.impl.ReadingService;

@RestController
@RequestMapping("/api/v1/readings")
@CrossOrigin(origins = "http://localhost:3000")
public class ReadingController {

    @Autowired
    private ReadingService readingService;

    @Operation(summary = "Crear lista de lectura inicial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista de lectura creada exitosamente"),
            @ApiResponse(responseCode = "412", description = "Datos inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingEntity createReading(@RequestParam("userId") @Valid String userId) {
        return readingService.createReadingEntity(userId);
    }

    @Operation(summary = "Añadir un nuevo genero en lista de lecturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genero añadido exitosamente"),
            @ApiResponse(responseCode = "412", description = "Datos inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto datos existentes")
    })
    @PutMapping("/add-genre")
    @ResponseStatus(HttpStatus.OK)
    public ReadingEntity createNewGenrerReading(@RequestBody @Valid GenreDTO dto) {
        return readingService.addGenreToReadingEntity(dto);
    }

    @Operation(summary = "Añade un nuevo libro a un genero de lecturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genero añadido exitosamente"),
            @ApiResponse(responseCode = "412", description = "Datos inválidos")
    })
    @PutMapping("/add-book")
    @ResponseStatus(HttpStatus.OK)
    public ReadingEntity addBookToGenre(@RequestBody @Valid BookDTO dto) {
        return readingService.addBookToGenre(dto);
    }

    @Operation(summary = "Elimina una lista de lectura según su genero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de lectura eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lista de lectura no encontrado")
    })
    @DeleteMapping("/genre/{userId}/{genre}")
    public ResponseEntity<String> deleteGenre(@PathVariable String userId, @PathVariable String genre) {
        String result = readingService.deleteGenre(userId, genre);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Eliminar un libro de una lista de lecturas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Lista de dectura o libro no encontrado")
    })
    @DeleteMapping("/book/{userId}/{genre}/{isbn}")
    public ResponseEntity<ReadingEntity> removeBook(@PathVariable String userId, @PathVariable String genre,
            @PathVariable String isbn) {
        return ResponseEntity.ok(readingService.removeBook(userId, genre, isbn));
    }

    @Operation(summary = "Obtener todas las lecturas de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de lecturas obtenida exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ReadingEntity> getReadingsByUserId(@PathVariable String userId) {
        ReadingEntity readings = readingService.getReadingsByUserId(userId);
        return ResponseEntity.ok(readings);
    }
}