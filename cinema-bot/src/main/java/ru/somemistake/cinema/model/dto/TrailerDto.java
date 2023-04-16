package ru.somemistake.cinema.model.dto;

import ru.somemistake.cinema.grpc.KinopoiskGrpc;

public record TrailerDto(
        String site,
        String url
) {

    public static TrailerDto of(KinopoiskGrpc.Trailer trailer) {
        return new TrailerDto(
                trailer.getSite(),
                trailer.getUrl()
        );
    }

}
