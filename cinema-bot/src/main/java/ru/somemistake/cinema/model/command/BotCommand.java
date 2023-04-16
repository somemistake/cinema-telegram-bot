package ru.somemistake.cinema.model.command;

public enum BotCommand {
    START("/start", "Подбор фильмов и сериалов, выберите команду"),
    RANDOM("/random", "Найти случайный фильм"),
    SEARCH("/parametersearch", "Поиск фильмов с указанными параметрами"),
    ADD("/addparameter", "Добавить параметр поиска"),
    CLEAR("/clearparameters", "Отчистить список параметров"),
    SHOW("/showparameters", "Показать список параметров"),
    ADD_FAVORITES("/addfavorites", "Добавить в избранное"),
    DELETE_FAVORITES("/deletefavorites", "Убрать из избранного"),
    DELETE_ALL_FAVORITES("/deleteallfavorites", "Убрать все из избранного"),
    SHOW_ALL_FAVORITES("/showfavorites", "Показать избранное");

    private final String command;
    private final String description;

    BotCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String command() {
        return command;
    }

    public String description() {
        return description;
    }
}
