package ru.somemistake.cinema.helpers;

import ru.somemistake.cinema.db.model.Favorite;
import ru.somemistake.cinema.model.api.Parameter;
import ru.somemistake.cinema.model.dto.MovieDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageHelpers {

    private static final Map<String, String> fields = new HashMap<>();

    static {
        fields.put("type", "тип");
        fields.put("persons.name", "имена актеров");
        fields.put("limit", "количество");
        fields.put("countries.name", "стран(ы)");
        fields.put("genres.name", "жанр(ы)");
        fields.put("year", "год(ы)");
    }

    public static String movie(MovieDto movie) {
        return String.format("ID - <code>%s</code>\n\n", movie.id()) +
                String.format("Тип - %s\n\n", movie.type()) +
                String.format("Название - %s\n\n", movie.name()) +
                String.format("Описание - %s\n\n", movie.description()) +
                String.format("Рейтинг Кинопоиск - %.2f\n\n", movie.ratingKp()) +
                String.format("Длительность - %d мин.\n\n", movie.duration()) +
                String.format("Год премьеры - %d\n\n", movie.premierYear()) +
                String.format("Страны - %s\n\n", movie.countries()) +
                String.format("Жанры - %s\n\n", movie.genres()) +
                String.format("Персоны - %s\n\n", movie.actors());
    }

    public static String parameters(List<Parameter> parameters) {
        return parameters.isEmpty() ?
                "Пусто" :
                parameters.stream()
                .map(parameter -> String.format("%s - %s", fields.get(parameter.field()), parameter.values()))
                .collect(Collectors.joining("\n"));
    }

    public static String favorites(List<Favorite> favorites) {
        return favorites.isEmpty() ?
                "Пусто" :
                favorites.stream()
                        .map(favorite -> String.format("ID - <code>%s</code>\nНазвание - %s", favorite.getTitleId(), favorite.getTitleName()))
                        .collect(Collectors.joining("\n\n"));
    }

}
