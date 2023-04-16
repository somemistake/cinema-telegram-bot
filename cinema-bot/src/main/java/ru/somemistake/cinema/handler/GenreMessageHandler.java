package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;
import ru.somemistake.cinema.state.OperationState;

import java.util.List;

@Component
public class GenreMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public GenreMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.ADD_GENRE_PARAMETER;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.saveChatOperationState(message.getChatId(), OperationState.GENRE_PARAMETER);
        cache.setChatBotState(message.getChatId(), BotState.PROCESS_MESSAGE_PARAMETERS);

        String text = "Введите жанр(ы), например:\nбоевик\nбоевик, комедия, ...";
        return List.of(SendMessage.builder().chatId(message.getChatId()).text(text).build());
    }
}
