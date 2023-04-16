package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class ClearMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public ClearMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.CLEAR_PARAMETERS;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.clearUserParameters(message.getChatId());
        cache.clearChatOperationState(message.getChatId());
        String text = "Параметры успешно очищены";
        return List.of(
                SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(text)
                        .build()
        );
    }
}
