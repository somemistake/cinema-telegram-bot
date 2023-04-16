package ru.somemistake.cinema.model.api;

public record VideoTypes(
        Video[] trailers,
        Video[] teasers
) {
}
