package ru.somemistake.cinema.processor;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.handler.MessageHandler;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class MessageProcessor {

    private final List<MessageHandler> handlers;
    private final ChatCache cache;

    public MessageProcessor(List<MessageHandler> handlers, ChatCache cache) {
        this.handlers = handlers;
        this.cache = cache;
    }

    public List<PartialBotApiMethod<Message>> processMessage(Message message) {
        BotState currentState = cache.getStateByChatId(message.getChatId());
        Optional<MessageHandler> handlerOpt = getHandler(currentState);
        return handlerOpt.map(handler -> handler.handleMessage(message)).orElse(Collections.emptyList());
    }

    private Optional<MessageHandler> getHandler(BotState currentState) {
        return handlers.stream().filter(handler -> handler.state().equals(currentState)).findFirst();
    }

}
