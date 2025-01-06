package us.es.reading.template;

import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    public void booksExternalApi(String isbn, int inReadingLists, String jwtToken) {
        String url = "http://57.152.88.187/api/v1/books/" + isbn + "/readingLists";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPatch patch = new HttpPatch(url);
            patch.addHeader("Authorization", "Bearer " + jwtToken);
            patch.addHeader("Content-Type", "application/json");

            String jsonBody = "{\"inReadingLists\":" + inReadingLists + "}";
            patch.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = client.execute(patch)) {
                int statusCode = response.getCode();                
            }
        } catch (Exception e) {
            System.err.println("Error al realizar la solicitud PATCH: " + e.getMessage());
        }
    }
}