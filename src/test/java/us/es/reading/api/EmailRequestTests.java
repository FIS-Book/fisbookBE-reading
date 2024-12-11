package us.es.reading.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmailRequestTests {

    // Return null after setting 'to' field to null
    @Test
    public void test_get_to_returns_null() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(null);
    
        String result = emailRequest.getTo();
    
        assertNull(result);
    }

    // Set valid email address string and verify it is stored correctly
    @Test
    public void test_set_valid_email_address() {
        EmailRequest emailRequest = new EmailRequest();
        String validEmail = "test@example.com";

        emailRequest.setTo(validEmail);

        assertEquals(validEmail, emailRequest.getTo());
    }

    // Set extremely long email address string
    @Test
    public void test_set_long_email_address() {
        EmailRequest emailRequest = new EmailRequest();
        String longEmail = "very".repeat(50) + "long@" + "domain".repeat(50) + ".com";

        emailRequest.setTo(longEmail);

        assertEquals(longEmail, emailRequest.getTo());
    }

    // Return subject containing special characters
    @Test
    public void test_returns_subject_with_special_chars() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject("Test@#$%^&*()");
    
        String result = emailRequest.getSubject();
    
        assertEquals("Test@#$%^&*()", result);
    }

    // Set subject with a regular string value
    @Test
    public void test_set_subject_with_regular_string() {
        EmailRequest emailRequest = new EmailRequest();
        String testSubject = "Test Email Subject";
    
        emailRequest.setSubject(testSubject);
    
        assertEquals(testSubject, emailRequest.getSubject());
    }

    // Set subject with null value
    @Test
    public void test_set_subject_with_null() {
        EmailRequest emailRequest = new EmailRequest();
    
        emailRequest.setSubject(null);
    
        assertNull(emailRequest.getSubject());
    }

    // Return very long string content (>1MB)
    @Test
    public void test_get_body_returns_large_content() {
        EmailRequest emailRequest = new EmailRequest();
        StringBuilder largeContent = new StringBuilder();
        int bodyLength = 1048576;
        for(int i = 0; i < bodyLength; i++) {
            largeContent.append("a");
        }
        String expectedBody = largeContent.toString();
        emailRequest.setBody(expectedBody);

        String actualBody = emailRequest.getBody();

        assertEquals(expectedBody, actualBody);
        assertTrue(actualBody.length() >= bodyLength);
    }

    // Set body with a regular string value and verify it is stored correctly
    @Test
    public void test_set_body_with_regular_string() {
        EmailRequest emailRequest = new EmailRequest();
        String testBody = "Test email body";
    
        emailRequest.setBody(testBody);
    
        assertEquals(testBody, emailRequest.getBody());
    }

    // Set body with null value
    @Test
    public void test_set_body_with_null() {
        EmailRequest emailRequest = new EmailRequest();
    
        emailRequest.setBody(null);
    
        assertNull(emailRequest.getBody());
    }
}
