package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.db.repository.FavoriteRepository;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class DeleteAllFavoritesMessageHandler implements MessageHandler {

    private final ChatCache cache;
    private final FavoriteRepository repository;

    public DeleteAllFavoritesMessageHandler(ChatCache cache, FavoriteRepository repository) {
        this.cache = cache;
        this.repository = repository;
    }

    @Override
    public BotState state() {
        return BotState.DELETE_ALL_FAVORITES;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.setDefaultState(message.getChatId());

        repository.deleteAll();

        return List.of(SendMessage.builder().chatId(message.getChatId()).text("Все фильмы убраны из избранного").build());
    }
}
