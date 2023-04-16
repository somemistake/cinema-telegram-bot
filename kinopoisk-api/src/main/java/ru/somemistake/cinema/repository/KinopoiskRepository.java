package ru.somemistake.cinema.repository;

import org.springframework.stereotype.Repository;
import ru.somemistake.cinema.config.EndpointConfig;
import ru.somemistake.cinema.model.api.Movie;
import ru.somemistake.cinema.model.api.MovieDocsResponseDto;
import ru.somemistake.cinema.util.ApiRequest;

@Repository
public class KinopoiskRepository {

    private final ApiRequest request;
    private final EndpointConfig endpointConfig;

    public KinopoiskRepository(ApiRequest request, EndpointConfig endpointConfig) {
        this.request = request;
        this.endpointConfig = endpointConfig;
    }

    public Movie getRandomMovie() {
        return request.GET(endpointConfig.getRandom(), Movie.class);
    }

    public MovieDocsResponseDto getMoviesByParameters(String parameters) {
        return request.GET(endpointConfig.getMovies() + parameters, MovieDocsResponseDto.class);
    }

    public Movie getMovieById(Long id) {
        return request.GET(endpointConfig.getMovieById() + id.toString(), Movie.class);
    }

}
