package ru.somemistake.cinema.helpers;

import ru.somemistake.cinema.model.api.Parameter;

import java.util.List;
import java.util.stream.Collectors;

public class GrpcHelpers {

    public static String grpcParameters(List<Parameter> parameters) {
        return parameters
                .stream()
                .flatMap(parameter -> parameter.values().stream().map(value -> String.format("&%s=%s", parameter.field(), value)))
                .collect(Collectors.joining());
    }

}
