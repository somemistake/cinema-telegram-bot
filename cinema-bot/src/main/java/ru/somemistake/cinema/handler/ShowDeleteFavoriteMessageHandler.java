package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class ShowDeleteFavoriteMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public ShowDeleteFavoriteMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.SHOW_DELETE_FAVORITE_MESSAGE;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.setChatBotState(message.getChatId(), BotState.DELETE_FAVORITES);

        String text = "Введите id фильмов, которые хотите убрать из избранного:";
        return List.of(SendMessage.builder().chatId(message.getChatId()).text(text).build());
    }
}
