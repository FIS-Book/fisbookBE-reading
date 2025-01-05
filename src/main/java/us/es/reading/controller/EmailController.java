package us.es.reading.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import us.es.reading.api.EmailRequest;
import us.es.reading.service.impl.SendGridEmailService;

@RestController
@RequestMapping("${api.version}"+"/email")
public class EmailController {    

    @Autowired
    private SendGridEmailService emailService;

    @Operation(summary = "Envío de meail automático")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Envío de email exitoso"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor"), 
            @ApiResponse(responseCode = "412", description = "Condición no cumplida")
    })
    @PostMapping
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        
        try {
            emailService.sendEmail(emailRequest.getFrom(), emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody(), emailRequest.getKeyEmail());
            return "Correo enviado correctamente a: " + emailRequest.getTo();
        } catch (IOException e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
