package us.es.reading.service;

import java.io.IOException;

public interface ISendGridEmailService {

    void sendEmail(String from, String to, String subject, String body) throws IOException;
}
