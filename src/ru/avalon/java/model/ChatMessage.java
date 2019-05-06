package ru.avalon.java.model;

/**
 * @author Daniel Alpatov
 */
public class ChatMessage extends AbstractMessage {

    private final String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
