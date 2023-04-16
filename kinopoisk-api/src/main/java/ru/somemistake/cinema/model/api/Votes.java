package ru.somemistake.cinema.model.api;

public record Votes(
        String kp,
        String imdb,
        int tmdb,
        int filmCritics,
        int russianFilmCritics,
        int await
) {
}
