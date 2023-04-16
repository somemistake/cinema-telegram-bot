package ru.somemistake.cinema.model.command;

public enum CallbackCommand {
    TYPE("Тип"),
    GENRE("Жанр"),
    COUNTRY("Страна"),
    YEAR("Год"),
    ACTOR("Актер"),
    COUNT("Количество");

    private final String description;

    CallbackCommand(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
