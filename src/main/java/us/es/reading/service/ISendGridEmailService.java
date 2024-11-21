package us.es.reading.service;

import java.io.IOException;

public interface ISendGridEmailService {

    void sendEmail(String to, String subject, String body) throws IOException;
}
