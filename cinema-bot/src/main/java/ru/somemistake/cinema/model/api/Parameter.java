package ru.somemistake.cinema.model.api;

import java.util.List;

public record Parameter(
        String field,
        List<String> values
) {

    public static Parameter of(String field, List<String> values) {
        return new Parameter(field, values);
    }
}
