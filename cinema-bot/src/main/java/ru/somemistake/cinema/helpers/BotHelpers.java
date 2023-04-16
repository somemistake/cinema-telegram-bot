package ru.somemistake.cinema.helpers;

import org.apache.commons.lang3.StringUtils;
import ru.somemistake.cinema.state.OperationState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BotHelpers {

    private static final Map<String, String> ru = new HashMap<>();
    private static final Map<String, String> en = new HashMap<>();

    static {
        ru.put("movie", "фильм");
        ru.put("tv-series", "сериал");
        ru.put("cartoon", "мультфильм");
        ru.put("mini-series", "минисериал");
        ru.put("anime", "аниме");
        ru.put("animated-series", "мультсериал");
        ru.put("tv-show", "тв шоу");

        en.put("фильм", "movie");
        en.put("сериал", "tv-series");
        en.put("мультфильм", "cartoon");
        en.put("минисериал", "mini-series");
        en.put("аниме", "anime");
        en.put("мультсериал", "animated-series");
        en.put("тв шоу", "tv-show");
    }

    public static String ruType(String type) {
        return ru.get(type);
    }

    public static String enType(String type) {
        return en.get(type);
    }

    public static boolean shouldCapitalize(OperationState currentState) {
        return currentState.equals(OperationState.ACTOR_PARAMETER) || currentState.equals(OperationState.COUNTRY_PARAMETER);
    }

    public static boolean isTypeParameter(OperationState currentState) {
        return currentState.equals(OperationState.TYPE_PARAMETER);
    }

    public static List<String> parseParameters(String text, boolean shouldCapitalize, boolean isTypeParameter) {
        return Arrays.stream(text.split(","))
                .map(
                        parameter -> Arrays.stream(parameter.trim().split(" "))
                                .map(word -> shouldCapitalize ?
                                        StringUtils.capitalize(word) :
                                        isTypeParameter ? enType(word) : word)
                                .collect(Collectors.joining("+"))
                )
                .filter(StringUtils::isNotEmpty)
                .toList();
    }

}
