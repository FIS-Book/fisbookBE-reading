package us.es.reading.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import us.es.reading.service.ISendGridEmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Service
public class SendGridEmailService implements ISendGridEmailService{

    @Autowired
    private Environment env;

    @Override
    public void sendEmail(String to, String subject, String body) throws IOException {
        Email from = new Email("edwareang@alum.us.es", "FIS-Book");

        String[] tos = to.split(",");
        Personalization personalization = new Personalization();
        for(int i = 0; i < tos.length; i++){
            if(tos[i].trim() != null && !tos[i].trim().isEmpty()){
                personalization.addTo(new Email(tos[i].trim()));
            }            
        }        

        Content content = new Content("text/plain", body);       
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setSubject(subject);
        mail.addContent(content);
        mail.addPersonalization(personalization);

        String sendGridApiKey = env.getProperty("spring.data.sendgrid.key");
        SendGrid sg = new SendGrid(sendGridApiKey); 
        Request request = new Request();

        try {
            request.setMethod(Method.POST); 
            request.setEndpoint("mail/send"); //endpoint de la API de SendGrid 
            request.setBody(mail.build()); 
            Response response = sg.api(request);
        } catch (IOException ex) {
            throw ex; 
        }
    }

}
