package fr.lpadmin.facture.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import fr.lpadmin.facture.model.lmstudio.Answer;
import fr.lpadmin.facture.model.lmstudio.ImageInput;
import fr.lpadmin.facture.model.lmstudio.Query;
import fr.lpadmin.facture.model.lmstudio.TextInput;

@Service
public class LmStudioService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LmStudioService.class);

    private final String model;

    private final RestClient restClient;


    public LmStudioService(
        @Value("${app.lmstudio.baseurl}") String baseUrl,
        @Value("${app.lmstudio.model}") String model,
        @Value("${app.lmstudio.connect-timeout-ms:5000}") int connectTimeoutMs,
        @Value("${app.lmstudio.read-timeout-ms:180000}") int readTimeoutMs
    ) {
        LOGGER.info("Initializing LmStudioService with baseUrl: {} and model: {}", baseUrl, model);
        this.model = model;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeoutMs);
        factory.setReadTimeout(readTimeoutMs);

        this.restClient = RestClient.builder()
        .requestFactory(factory)//new JdkClientHttpRequestFactory(httpClient))
            .baseUrl(baseUrl)
            .build();
    }

    public Answer chat(String message) {
        Query query = new Query();
        query.setModel(this.model);
        query.addInput(new TextInput(message));

        return this.chat(query);
    }

    public Answer chat(Query query) {
        if(query.getModel() == null) {
            query.setModel(this.model);
        }
        return this.restClient.post()
            .uri("/api/v1/chat")
            .contentType(MediaType.APPLICATION_JSON)
            .body(query)
            .retrieve()
            .body(Answer.class);
    }

    public Answer chatOnImg(String message, String image) {
        Query query = new Query();
        query.setModel(this.model);
        query.addInput(new TextInput(message));
        query.addInput(new ImageInput("data:image/png;base64," + image));

        return this.chat(query);
    }

    public Answer chatOnText(String message, String content) {
        Query query = new Query();
        query.setModel(this.model);
        query.addInput(new TextInput(message));
        query.addInput(new TextInput(content));

        return this.chat(query);
    }

    public Answer chatOnTextImg(String message, String content, String image) {
        Query query = new Query();
        query.setModel(this.model);
        query.addInput(new TextInput(message));
        query.addInput(new TextInput(content));
        query.addInput(new ImageInput("data:image/png;base64," + image));

        return this.chat(query);
    }

}
