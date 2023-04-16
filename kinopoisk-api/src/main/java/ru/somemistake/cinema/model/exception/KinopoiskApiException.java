package ru.somemistake.cinema.model.exception;

public class KinopoiskApiException extends RuntimeException {
    public KinopoiskApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
