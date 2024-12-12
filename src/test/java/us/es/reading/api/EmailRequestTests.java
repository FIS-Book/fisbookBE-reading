package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmailRequestTests {

    private EmailRequest emailRequest;

    @BeforeEach
    public void setUp() {
        emailRequest = new EmailRequest();
        emailRequest.setTo("estudiante@us.es");
        emailRequest.setSubject("subject");
        emailRequest.setBody("body");
    }

    @Test
    public void test_get_to_returns_null_all_values() {
        emailRequest = new EmailRequest();

        assertNull(emailRequest.getTo());
        assertNull(emailRequest.getSubject());
        assertNull(emailRequest.getBody());
    }

    @Test
    public void test_get_to_returns_notnull_all_values() {
        assertNotNull(emailRequest.getTo());
        assertNotNull(emailRequest.getSubject());
        assertNotNull(emailRequest.getBody());
    }

    @Test
    public void test_valid_returns_true_when_is_initialized() {
        assertEquals("estudiante@us.es", emailRequest.getTo());
        assertEquals("subject", emailRequest.getSubject());
        assertEquals("body", emailRequest.getBody());
    }

    @Test
    public void test_valid_returns_false_when_is_initialized() {
        assertFalse("estudiante@us.esa".equalsIgnoreCase(emailRequest.getTo()));
        assertFalse("subjecta".equalsIgnoreCase(emailRequest.getSubject()));
        assertFalse("bodya".equalsIgnoreCase(emailRequest.getBody()));
    }

    @Test
    public void test_valid_email_character() {
        assertTrue(emailRequest.getTo().contains("@"));
    }
}
