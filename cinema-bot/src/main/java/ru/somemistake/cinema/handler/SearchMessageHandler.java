package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.model.api.Parameter;
import ru.somemistake.cinema.model.dto.MovieDto;
import ru.somemistake.cinema.model.exception.CinemaBotException;
import ru.somemistake.cinema.model.keyboard.KeyboardHandler;
import ru.somemistake.cinema.kinopoisk.repository.KinopoiskRepository;
import ru.somemistake.cinema.helpers.MessageHelpers;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

@Component
public class SearchMessageHandler implements MessageHandler {

    private final KinopoiskRepository repository;
    private final KeyboardHandler keyboardHandler;
    private final ChatCache cache;

    public SearchMessageHandler(KinopoiskRepository repository, KeyboardHandler keyboardHandler, ChatCache cache) {
        this.repository = repository;
        this.keyboardHandler = keyboardHandler;
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.PARAMETERS_SEARCH;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        List<Parameter> userParameters = cache.getUserParameters(message.getChatId());
        List<MovieDto> moviesByParameters = repository.getMoviesByParameters(userParameters);

        cache.clearChatOperationState(message.getChatId());
        cache.setDefaultState(message.getChatId());

        return moviesByParameters.stream().flatMap(movie -> {
            InputStream urlInputStream;
            try {
                urlInputStream = new URL(movie.posterUrl()).openStream();
            } catch (Exception exception) {
                throw new CinemaBotException("Cannot create url to trailer", exception);
            }

            return Stream.of(
                    SendPhoto.builder()
                            .chatId(message.getChatId())
                            .photo(new InputFile(urlInputStream, "Постер"))
                            .build(),
                    SendMessage.builder()
                            .chatId(message.getChatId())
                            .text(MessageHelpers.movie(movie))
                            .parseMode("HTML")
                            .replyMarkup(keyboardHandler.getTrailersKeyboard(movie.trailers()))
                            .build()
            );
        }).toList();
    }
}
