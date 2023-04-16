package ru.somemistake.cinema.model.api;

public record MovieDocsResponseDto(
        Movie[] docs,
        int total,
        int limit,
        int page,
        int pages
) {
}
