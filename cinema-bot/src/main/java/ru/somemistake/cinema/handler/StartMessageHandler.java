package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

import static ru.somemistake.cinema.model.command.BotCommand.START;

@Component
public class StartMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public StartMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.START;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.setDefaultState(message.getChatId());
        return List.of(SendMessage.builder().chatId(message.getChatId()).text(START.description()).build());
    }
}
