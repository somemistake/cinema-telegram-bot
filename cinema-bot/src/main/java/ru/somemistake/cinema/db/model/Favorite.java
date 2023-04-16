package ru.somemistake.cinema.db.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Favorite {

    @Id
    private String id;
    private Long chatId;
    private Long titleId;
    private String titleName;

    public Favorite() {

    }

    public Favorite(Long chatId, Long titleId, String titleName) {
        this.chatId = chatId;
        this.titleId = titleId;
        this.titleName = titleName;
    }

    public Favorite(String id, Long chatId, Long titleId, String titleName) {
        this.id = id;
        this.chatId = chatId;
        this.titleId = titleId;
        this.titleName = titleName;
    }

    public static Favorite of(Long chatId, Long titleId, String titleName) {
        return new Favorite(chatId, titleId, titleName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
}
