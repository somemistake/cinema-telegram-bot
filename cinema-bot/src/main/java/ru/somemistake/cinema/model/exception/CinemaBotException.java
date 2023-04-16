package ru.somemistake.cinema.model.exception;

public class CinemaBotException extends RuntimeException {
    public CinemaBotException(String message, Throwable cause) {
        super(message, cause);
    }
}
