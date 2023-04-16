package ru.somemistake.cinema.handler;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.somemistake.cinema.state.BotState;

import java.util.List;

public interface MessageHandler {
    BotState state();
    List<PartialBotApiMethod<Message>> handleMessage(Message message);
}
