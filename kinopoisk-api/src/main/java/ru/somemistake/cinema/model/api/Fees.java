package ru.somemistake.cinema.model.api;

public record Fees(
        CurrencyValue world,
        CurrencyValue russia,
        CurrencyValue usa
) {
}
