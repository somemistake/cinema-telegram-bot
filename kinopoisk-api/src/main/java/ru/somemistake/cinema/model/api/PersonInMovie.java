package ru.somemistake.cinema.model.api;

public record PersonInMovie(
        int id,
        String photo,
        String name,
        String enName,
        String description,
        String profession,
        String enProfession
) {
}
