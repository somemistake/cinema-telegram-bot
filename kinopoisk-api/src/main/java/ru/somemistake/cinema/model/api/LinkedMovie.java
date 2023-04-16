package ru.somemistake.cinema.model.api;

public record LinkedMovie(
        int id,
        String name,
        String enName,
        String alternativeName,
        String type,
        ShortImage poster
) {
}
