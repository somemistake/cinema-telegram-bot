package ru.somemistake.cinema.state;

import org.springframework.stereotype.Component;
import ru.somemistake.cinema.model.api.Parameter;
import ru.somemistake.cinema.model.command.BotCommand;
import ru.somemistake.cinema.model.command.CallbackCommand;

import java.util.*;

@Component
public class ChatCache {

    private final Map<Long, BotState> botStateCache = new HashMap<>();
    private final Map<Long, List<Parameter>> searchParameterCache = new HashMap<>();
    private final Map<OperationState, String> fieldCache = new HashMap<>();
    private final Map<String, BotState> commandStateCache = new HashMap<>();
    private final Map<String, BotState> callbackStateCache = new HashMap<>();
    private final Map<Long, OperationState> chatCurrentOperationStateCache = new HashMap<>();

    public ChatCache() {
        fieldCache.put(OperationState.TYPE_PARAMETER, "type");
        fieldCache.put(OperationState.ACTOR_PARAMETER, "persons.name");
        fieldCache.put(OperationState.COUNT_PARAMETER, "limit");
        fieldCache.put(OperationState.COUNTRY_PARAMETER, "countries.name");
        fieldCache.put(OperationState.GENRE_PARAMETER, "genres.name");
        fieldCache.put(OperationState.YEAR_PARAMETER, "year");

        commandStateCache.put(BotCommand.START.command(), BotState.START);
        commandStateCache.put(BotCommand.RANDOM.command(), BotState.RANDOM_SEARCH);
        commandStateCache.put(BotCommand.SEARCH.command(), BotState.PARAMETERS_SEARCH);
        commandStateCache.put(BotCommand.ADD.command(), BotState.ADD_PARAMETER);
        commandStateCache.put(BotCommand.CLEAR.command(), BotState.CLEAR_PARAMETERS);
        commandStateCache.put(BotCommand.SHOW.command(), BotState.SHOW_PARAMETERS);
        commandStateCache.put(BotCommand.ADD_FAVORITES.command(), BotState.SHOW_ADD_FAVORITE_MESSAGE);
        commandStateCache.put(BotCommand.DELETE_FAVORITES.command(), BotState.SHOW_DELETE_FAVORITE_MESSAGE);
        commandStateCache.put(BotCommand.SHOW_ALL_FAVORITES.command(), BotState.SHOW_ALL_FAVORITES);
        commandStateCache.put(BotCommand.DELETE_ALL_FAVORITES.command(), BotState.DELETE_ALL_FAVORITES);

        callbackStateCache.put(CallbackCommand.TYPE.description(), BotState.ADD_TYPE_PARAMETER);
        callbackStateCache.put(CallbackCommand.GENRE.description(), BotState.ADD_GENRE_PARAMETER);
        callbackStateCache.put(CallbackCommand.COUNTRY.description(), BotState.ADD_COUNTRY_PARAMETER);
        callbackStateCache.put(CallbackCommand.YEAR.description(), BotState.ADD_YEAR_PARAMETER);
        callbackStateCache.put(CallbackCommand.ACTOR.description(), BotState.ADD_ACTOR_PARAMETER);
        callbackStateCache.put(CallbackCommand.COUNT.description(), BotState.ADD_COUNT_PARAMETER);
    }

    public OperationState getOperationByChatId(Long chatId) {
        return chatCurrentOperationStateCache.get(chatId);
    }

    public void saveChatOperationState(Long chatId, OperationState operationState) {
        chatCurrentOperationStateCache.put(chatId, operationState);
    }

    public void clearChatOperationState(Long chatId) {
        chatCurrentOperationStateCache.remove(chatId);
    }

    public void saveStateFromCommand(Long chatId, String command) {
        BotState state = commandStateCache.getOrDefault(command, getStateByChatId(chatId));
        botStateCache.put(chatId, state);
    }

    public void saveStateFromCallback(Long chatId, String callback) {
        BotState state = callbackStateCache.getOrDefault(callback, getStateByChatId(chatId));
        botStateCache.put(chatId, state);
    }

    public List<Parameter> getUserParameters(Long chatId) {
        return searchParameterCache.getOrDefault(chatId, Collections.emptyList());
    }

    public void clearUserParameters(Long chatId) {
        searchParameterCache.remove(chatId);
    }

    public void saveParameters(Long chatId, List<String> values) {
        List<Parameter> chatParameters = searchParameterCache.getOrDefault(chatId, Collections.list(Collections.emptyEnumeration()));
        String field = fieldCache.getOrDefault(getOperationByChatId(chatId), "default");
        Optional<Parameter> parameterOpt = chatParameters
                .stream()
                .filter(parameter -> Objects.nonNull(parameter.field()) && parameter.field().equals(field))
                .peek(parameter -> parameter.values().addAll(values))
                .findFirst();
        if (parameterOpt.isEmpty() && !values.isEmpty()) {
            chatParameters.add(Parameter.of(field, values));
        }
        searchParameterCache.put(chatId, chatParameters);
    }

    public BotState getStateByChatId(Long chatId) {
        return botStateCache.getOrDefault(chatId, BotState.NO_STATE);
    }

    public void setChatBotState(Long chatId, BotState state) {
        botStateCache.put(chatId, state);
    }

    public void setDefaultState(Long chatId) {
        setChatBotState(chatId, BotState.NO_STATE);
    }

}
