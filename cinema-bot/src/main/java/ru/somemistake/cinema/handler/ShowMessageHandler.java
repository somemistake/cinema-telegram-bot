package ru.somemistake.cinema.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.model.api.Parameter;
import ru.somemistake.cinema.helpers.MessageHelpers;
import ru.somemistake.cinema.state.BotState;
import ru.somemistake.cinema.state.ChatCache;

import java.util.List;

import static ru.somemistake.cinema.model.command.BotCommand.CLEAR;

@Component
public class ShowMessageHandler implements MessageHandler {

    private final ChatCache cache;

    public ShowMessageHandler(ChatCache cache) {
        this.cache = cache;
    }

    @Override
    public BotState state() {
        return BotState.SHOW_PARAMETERS;
    }

    @Override
    public List<PartialBotApiMethod<Message>> handleMessage(Message message) {
        List<Parameter> parameters = cache.getUserParameters(message.getChatId());
        String text =
                String.format("Для очищения списка параметров воспользуйтесь командой %s\n\n", CLEAR.command()) +
                        String.format("Список параметров:\n\n%s", MessageHelpers.parameters(parameters));
        cache.setDefaultState(message.getChatId());
        return  List.of(SendMessage.builder().chatId(message.getChatId()).text(text).build());
    }
}
