package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.helpers.BotHelpers;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;
import ru.somemistake.cinema.state.OperationState;

import java.util.List;

@Component
public class ParameterMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public ParameterMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.PROCESS_MESSAGE_PARAMETERS;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        OperationState operationState = cache.getOperationByChatId(message.getChatId());

        String text = message.getText().toLowerCase();

        boolean shouldCapitalize = BotHelpers.shouldCapitalize(operationState);
        boolean isTypeParameter = BotHelpers.isTypeParameter(operationState);

        List<String> values = BotHelpers.parseParameters(text, shouldCapitalize, isTypeParameter);

        cache.saveParameters(message.getChatId(), values);
        cache.setDefaultState(message.getChatId());
        cache.clearChatOperationState(message.getChatId());

        return List.of(SendMessage.builder().chatId(message.getChatId()).text("Параметр(ы) успешно добавлен").build());
    }
}
