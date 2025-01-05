package us.es.reading.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import us.es.reading.api.EmailRequest;
import us.es.reading.service.impl.SendGridEmailService;

public class EmailControllerTests {

    @Mock
    private SendGridEmailService sendGridEmailService;

    @InjectMocks
    private EmailController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Valid email request with all required fields returns success message with recipient address
    @Test
    public void test_valid_email_request_returns_success() throws IOException {
        EmailRequest request = new EmailRequest();
        request.setKeyEmail("fisbook2025");
        request.setFrom("sender@test.com");
        request.setTo("recipient@test.com"); 
        request.setSubject("Test Subject");
        request.setBody("Test Body");    

        String response = controller.sendEmail(request);

        verify(sendGridEmailService).sendEmail(
            request.getFrom(),
            request.getTo(), 
            request.getSubject(),
            request.getBody(), request.getKeyEmail()
        );
        assertEquals(response, "Correo enviado correctamente a: recipient@test.com");
    }

    // Empty or null values in EmailRequest fields
    @Test
    public void test_null_email_fields_throws_exception() throws IOException {
        EmailRequest request = new EmailRequest();
        request.setKeyEmail("");
        request.setFrom(null);
        request.setTo("");
        request.setSubject(null);
        request.setBody("");       

        doThrow(new IOException("Invalid email fields"))
            .when(sendGridEmailService)
            .sendEmail(null, "", null, "", "");

        String response = controller.sendEmail(request);

        verify(sendGridEmailService).sendEmail(null, "", null, "", "");
        assertEquals(response, "Error al enviar el correo: Invalid email fields");
    }
}
