package ru.somemistake.cinema.model.api;

public record Video(
        String url,
        String name,
        String site,
        String type,
        int size
) {
}
