package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.db.model.Favorite;
import ru.somemistake.cinema.db.repository.FavoriteRepository;
import ru.somemistake.cinema.helpers.BotHelpers;
import ru.somemistake.cinema.kinopoisk.repository.KinopoiskRepository;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class AddFavoriteMessageHandler implements MessageHandler {

    private final ChatCache cache;
    private final FavoriteRepository dbRepository;
    private final KinopoiskRepository apiRepository;

    public AddFavoriteMessageHandler(ChatCache cache, FavoriteRepository dbRepository, KinopoiskRepository apiRepository) {
        this.cache = cache;
        this.dbRepository = dbRepository;
        this.apiRepository = apiRepository;
    }

    @Override
    public BotState state() {
        return BotState.ADD_FAVORITES;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        String text = message.getText().toLowerCase();
        List<Long> ids = BotHelpers.parseParameters(text, false, false)
                .stream().map(Long::parseLong).toList();

        List<Favorite> favorites = ids.stream()
                .map(apiRepository::getMovieById)
                .map(movie -> Favorite.of(message.getChatId(), movie.id(), movie.name()))
                .toList();

        dbRepository.saveAll(favorites);

        cache.setDefaultState(message.getChatId());

        return List.of(SendMessage.builder().chatId(message.getChatId()).text("Фильм(ы) добавлены в избранное").build());
    }
}
