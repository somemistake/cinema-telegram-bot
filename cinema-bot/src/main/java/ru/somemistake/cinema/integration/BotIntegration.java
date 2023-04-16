package ru.somemistake.cinema.integration;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.somemistake.cinema.processor.MessageProcessor;
import ru.somemistake.cinema.state.ChatCache;

import java.util.Collections;
import java.util.List;

@Service
public class BotIntegration {

    private final MessageProcessor messageProcessor;
    private final ChatCache cache;

    public BotIntegration(MessageProcessor messageProcessor, ChatCache cache) {
        this.messageProcessor = messageProcessor;
        this.cache = cache;
    }

    public List<PartialBotApiMethod<Message>> processUpdate(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            cache.saveStateFromCommand(message.getChatId(), message.getText());
            return messageProcessor.processMessage(message);
        }

        if (update.hasCallbackQuery()) {
            CallbackQuery query = update.getCallbackQuery();
            Message message = query.getMessage();
            cache.saveStateFromCallback(message.getChatId(), query.getData());
            return messageProcessor.processMessage(message);
        }

        return Collections.emptyList();
    }

}
