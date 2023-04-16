package ru.somemistake.cinema.model.api;

public record ReviewInfo(
        int count,
        int positiveCount,
        String percentage
) {
}
