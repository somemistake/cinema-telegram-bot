package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.db.repository.FavoriteRepository;
import ru.somemistake.cinema.helpers.BotHelpers;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class DeleteFavoriteMessageHandler implements MessageHandler {

    private final ChatCache cache;
    private final FavoriteRepository repository;

    public DeleteFavoriteMessageHandler(ChatCache cache, FavoriteRepository repository) {
        this.cache = cache;
        this.repository = repository;
    }

    @Override
    public BotState state() {
        return BotState.DELETE_FAVORITES;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        String text = message.getText().toLowerCase();
        List<Long> ids = BotHelpers.parseParameters(text, false, false)
                .stream().map(Long::parseLong).toList();

        repository.deleteByTitleIdIn(ids);

        cache.setDefaultState(message.getChatId());

        return List.of(SendMessage.builder().chatId(message.getChatId()).text("Выбранный(ые) фильм(ы) убраны из избранного").build());
    }
}
