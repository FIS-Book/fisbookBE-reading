package us.es.reading.service.impl;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import us.es.reading.service.ISendGridEmailService;
import us.es.reading.utils.FieldsValidate;

@Service
public class SendGridEmailService implements ISendGridEmailService{

    @Autowired
    private Environment env;

    private Email email;

    @Override
    public void sendEmail(String from, String to, String subject, String body, String emailKey) throws IOException {
        FieldsValidate.validateFieldsToSendEmail(from, to, subject, body, emailKey);
        String dt = Objects.nonNull(from)&&StringUtils.isNotBlank(from)?from:"edwareang@alum.us.es";
        email = new Email(dt, "FIS-Book");

        String[] tos = to.split(",");
        Personalization personalization = new Personalization();
        for(int i = 0; i < tos.length; i++){
            if(tos[i].trim() != null && !tos[i].trim().isEmpty()){
                personalization.addTo(new Email(tos[i].trim()));
            }            
        }        

        Content content = new Content("text/plain", body);       
        Mail mail = new Mail();
        mail.setFrom(email);
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
            System.out.println(response.getStatusCode());
        } catch (IOException ex) {
            throw ex; 
        }
    }
}
