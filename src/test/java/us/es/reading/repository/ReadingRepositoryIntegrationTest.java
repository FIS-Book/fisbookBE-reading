package us.es.reading.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class ReadingRepositoryIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    void test_connection() {
        // Verificar que la conexión está activa
        assertNotNull(mongoTemplate);
        assertEquals("fisbook_reading", mongoTemplate.getDb().getName());
    }

    @Test
    void test_list_collections() {
        // Listar todas las colecciones de la base de datos
        List<String> collections = mongoTemplate.getDb().listCollectionNames().into(new ArrayList<>());
        
        // Verificar que al menos una colección exista
        assertTrue(collections.size() > 0);        
    }

    @Test
    void test_find_collection_by_name() {
        // Nombre de la colección que se desea buscar
        String collectionName = "readings"; 
        
        // Verificar si la colección existe
        boolean collectionExists = mongoTemplate.getDb().listCollectionNames()
                                                  .into(new ArrayList<>())
                                                  .contains(collectionName);

        // Assert que la colección existe
        assertTrue(collectionExists, "La colección no existe: " + collectionName);
    }

    @Test
    void test__find_all_documents_from_collection() {
        // Nombre de la colección
        String collectionName = "readings"; // Cambia esto al nombre de tu colección
        
        // Consultar todos los documentos de la colección
        List<Object> documents = mongoTemplate.findAll(Object.class, collectionName);
        
        // Asegúrate de que al menos hay un documento
        assertTrue(documents.size() > 0, "No hay documentos en la colección: " + collectionName);  
    }
}
