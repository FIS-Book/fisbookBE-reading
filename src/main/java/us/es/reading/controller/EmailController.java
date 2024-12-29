package us.es.reading.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import us.es.reading.api.EmailRequest;
import us.es.reading.service.impl.SendGridEmailService;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {

    @Autowired
    private SendGridEmailService emailService;

    @PostMapping
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
            return "Correo enviado correctamente a: " + emailRequest.getTo();
        } catch (IOException e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
