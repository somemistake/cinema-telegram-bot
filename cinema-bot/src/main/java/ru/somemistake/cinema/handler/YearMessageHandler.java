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
public class YearMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public YearMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.ADD_YEAR_PARAMETER;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        cache.saveChatOperationState(message.getChatId(), OperationState.YEAR_PARAMETER);
        cache.setChatBotState(message.getChatId(), BotState.PROCESS_MESSAGE_PARAMETERS);

        String text = "Введите год(ы), например:\n2018\n2018-2022";
        return List.of(SendMessage.builder().chatId(message.getChatId()).text(text).build());
    }
}
