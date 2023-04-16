package ru.somemistake.cinema.model.keyboard;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.somemistake.cinema.model.dto.TrailerDto;
import ru.somemistake.cinema.model.exception.CinemaBotException;
import ru.somemistake.cinema.model.command.CallbackCommand;

import java.util.List;

import static ru.somemistake.cinema.model.command.CallbackCommand.*;

@Component
public class KeyboardHandler {

    public InlineKeyboardMarkup getTrailersKeyboard(List<TrailerDto> trailers) {
        List<List<InlineKeyboardButton>> keyboard =
                trailers
                        .stream()
                        .map(trailer -> List.of(InlineKeyboardButton.builder().text(trailer.site()).url(trailer.url()).build()))
                        .toList();

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    public InlineKeyboardMarkup getParametersKeyboard() {

        List<CallbackCommand> commands = List.of(TYPE, GENRE, COUNTRY, YEAR, ACTOR, COUNT);

        List<List<InlineKeyboardButton>> keyboard = Lists.partition(commands, 2)
                .stream()
                .map(
                        partitionCommands -> partitionCommands
                                .stream()
                                .map(
                                        command -> {
                                            try {
                                                return InlineKeyboardButton.builder()
                                                        .text(command.description())
                                                        .callbackData(command.description())
                                                        .build();
                                            } catch (Exception exception) {
                                                throw new CinemaBotException("Cannot create parameters keyboard", exception);
                                            }
                                        }
                                )
                                .toList()
                )
                .toList();

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

}
