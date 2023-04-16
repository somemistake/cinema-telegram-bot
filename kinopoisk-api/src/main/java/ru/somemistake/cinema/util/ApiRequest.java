package ru.somemistake.cinema.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.somemistake.cinema.config.KinopoiskConfig;
import ru.somemistake.cinema.model.exception.KinopoiskApiException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class ApiRequest {

    private final HttpClient client;
    private final HttpRequest.Builder request;
    private final ObjectMapper mapper;

    public ApiRequest(KinopoiskConfig kinopoiskConfig) {
        this.client = HttpClient.newHttpClient();
        this.request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("X-API-KEY", kinopoiskConfig.getToken());
        this.mapper = new ObjectMapper();
    }

    public <T> T GET(String uri, Class<T> responseClass) {
        HttpRequest apiRequest = request.uri(URI.create(uri)).GET().build();
        T response;
        try {
            String json = client.send(apiRequest, HttpResponse.BodyHandlers.ofString()).body();
            response = mapper.readValue(json, responseClass);
        } catch (Exception exception) {
            throw new KinopoiskApiException(String.format("Cannot get %s from Kinopoisk", responseClass.getName()), exception);
        }
        return response;
    }

}
