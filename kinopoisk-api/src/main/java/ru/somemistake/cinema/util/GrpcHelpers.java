package ru.somemistake.cinema.util;

import ru.somemistake.cinema.grpc.KinopoiskGrpc;
import ru.somemistake.cinema.model.api.*;

import java.util.*;

public class GrpcHelpers {

    private static final Map<String, String> ru = new HashMap<>();

    private static final String EMPTY_TEXT_FIELD = "Отсутствует";
    private static final Double EMPTY_DECIMAL_FIELD = 0d;
    private static final String EMPTY_POSTER_URL = "http://www.we-are-scout.com/wp-content/uploads/2015/08/NO_poster-600x802.jpg";

    static {
        ru.put("movie", "фильм");
        ru.put("tv-series", "сериал");
        ru.put("cartoon", "мультфильм");
        ru.put("mini-series", "минисериал");
        ru.put("anime", "аниме");
        ru.put("animated-series", "мультсериал");
        ru.put("tv-show", "тв шоу");
    }

    public static String ruType(String type) {
        return ru.get(type);
    }

    public static KinopoiskGrpc.Movie grpcMovie(Movie movie) {
        return KinopoiskGrpc.Movie.newBuilder()
                .setType(GrpcHelpers.ruType(movie.type()))
                .setName(movie.name() == null ? EMPTY_TEXT_FIELD : movie.name())
                .setDescription(movie.description() == null ? movie.shortDescription() == null ? EMPTY_TEXT_FIELD : movie.shortDescription() : movie.description())
                .setRatingKp(movie.rating() == null ? EMPTY_DECIMAL_FIELD : movie.rating().kp())
                .setDuration(movie.movieLength())
                .setPremiereYear(movie.year())
                .addAllCountries(GrpcHelpers.listNames(movie.countries()))
                .addAllGenres(GrpcHelpers.listNames(movie.genres()))
                .addAllActors(GrpcHelpers.actorNames(movie.persons()))
                .addAllTrailers(GrpcHelpers.trailers(movie.videos() == null ? new Video[0] : movie.videos().trailers()))
                .setPosterUrl(movie.poster() == null ? EMPTY_POSTER_URL : movie.poster().url())
                .setTitleId(movie.id())
                .build();
    }

    public static List<KinopoiskGrpc.Trailer> trailers(Video[] trailers) {
        return Arrays.stream(trailers)
                .map(trailer ->
                        KinopoiskGrpc.Trailer.newBuilder()
                                .setSite(Objects.equals(trailer.site(), "unknown") ? "трейлер" : trailer.site())
                                .setUrl(trailer.url()).build()
                )
                .toList();
    }

    public static List<String> listNames(ItemName[] itemNames) {
        return Arrays.stream(itemNames)
                .map(ItemName::name)
                .toList();
    }

    public static List<String> actorNames(PersonInMovie[] actors) {
        return Arrays.stream(actors)
                .filter(actor -> Objects.nonNull(actor.name()))
                .limit(10)
                .map(PersonInMovie::name)
                .toList();
    }

}
