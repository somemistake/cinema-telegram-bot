package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.model.keyboard.KeyboardHandler;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

@Component
public class AddMessageHandler implements MessageHandler {

    private final KeyboardHandler keyboardHandler;
    private final ChatCache cache;

    public AddMessageHandler(KeyboardHandler keyboardHandler, ChatCache cache) {
        this.keyboardHandler = keyboardHandler;
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.ADD_PARAMETER;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        String text = "Выберите параметр поиска из списка ниже:";
        cache.setDefaultState(message.getChatId());
        return List.of(
                SendMessage.builder()
                        .chatId(message.getChatId())
                        .text(text)
                        .replyMarkup(keyboardHandler.getParametersKeyboard())
                        .build()
        );
    }
}
