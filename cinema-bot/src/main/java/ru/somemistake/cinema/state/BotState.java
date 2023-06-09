package ru.somemistake.cinema.state;

public enum BotState {
    NO_STATE, START,

    ADD_PARAMETER, CLEAR_PARAMETERS, RANDOM_SEARCH,
    PARAMETERS_SEARCH, SHOW_PARAMETERS,
    PROCESS_MESSAGE_PARAMETERS,

    SHOW_ADD_FAVORITE_MESSAGE, ADD_FAVORITES,
    SHOW_DELETE_FAVORITE_MESSAGE, DELETE_FAVORITES,
    SHOW_ALL_FAVORITES, DELETE_ALL_FAVORITES,

    ADD_ACTOR_PARAMETER, ADD_COUNT_PARAMETER, ADD_COUNTRY_PARAMETER,
    ADD_GENRE_PARAMETER, ADD_TYPE_PARAMETER, ADD_YEAR_PARAMETER
}
