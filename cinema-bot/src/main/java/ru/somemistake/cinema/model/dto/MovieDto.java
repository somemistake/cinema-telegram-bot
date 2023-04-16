package ru.somemistake.cinema.model.dto;

import ru.somemistake.cinema.grpc.KinopoiskGrpc;

import java.util.List;

public record MovieDto(
        long id,
        String type,
        String name,
        String description,
        double ratingKp,
        long duration,
        long premierYear,
        List<String> genres,
        List<String> actors,
        List<String> countries,
        String posterUrl,
        List<TrailerDto> trailers
) {

    public static MovieDto of(KinopoiskGrpc.Movie movie) {
        return new MovieDto(
                movie.getTitleId(),
                movie.getType(),
                movie.getName(),
                movie.getDescription(),
                movie.getRatingKp(),
                movie.getDuration(),
                movie.getPremiereYear(),
                movie.getGenresList(),
                movie.getActorsList(),
                movie.getCountriesList(),
                movie.getPosterUrl(),
                movie.getTrailersList().stream().map(TrailerDto::of).toList()
        );
    }
}
