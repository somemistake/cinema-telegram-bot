package ru.somemistake.cinema.model.api;

public record Rating(
        int kp,
        int imdb,
        int tmdb,
        int filmCritics,
        int russianFilmCritics,
        int await
) {
}
