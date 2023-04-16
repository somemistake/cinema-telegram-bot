package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class ShowAddFavoriteMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public ShowAddFavoriteMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.SHOW_ADD_FAVORITE_MESSAGE;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.setChatBotState(message.getChatId(), BotState.ADD_FAVORITES);

        String text = "Введите id фильмов, которые хотите добавить в избранное:";
        return List.of(SendMessage.builder().chatId(message.getChatId()).text(text).build());
    }
}
