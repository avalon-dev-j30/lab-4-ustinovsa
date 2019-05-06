package ru.avalon.java.net;

import ru.avalon.java.net.messaging.MessagingChannel;
import ru.avalon.java.threading.Asynchronous;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import static java.util.Collections.synchronizedCollection;

/**
 * Реализация соединения на основе существующего канала обмена сообщениями.
 *
 * @author Daniel Alpatov
 */
class ConnectionImpl implements Asynchronous, Connection, Connection.StateListener, Connection.MessageListener {

    /**
     * Канал обмена сообщениями.
     */
    private final MessagingChannel channel;

    /**
     * Коллекция обработчиков модели событий изменения
     * состояния соединения.
     */
    private final Collection<StateListener> stateListeners;

    /**
     * Коллекция обработчиков модели событий получения
     * входящих сообщений.
     */
    private final Collection<MessageListener> messageListeners;

    /**
     * Инициализирует экземпляр класса на основе созданного
     * канала обмена сообщениями.
     *
     * @param channel канал обмена сообщениями. Не может быть {@code null}.
     *
     * @throws IOException
     * @throws IllegalArgumentException, если {@code channel} равен {@code null}.
     */
    ConnectionImpl(MessagingChannel channel) throws IOException {
        if (channel == null) {
            throw new IllegalArgumentException("Channel can not be 'null'!");
        }
        this.channel = channel;
        stateListeners = synchronizedCollection(new HashSet<>());
        messageListeners = synchronizedCollection(new HashSet<>());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnectionClose(Connection connection) {
        for (StateListener listener : stateListeners) {
            listener.onConnectionClose(connection);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMessage(Connection connection, Object message) {
        for (MessageListener listener : messageListeners) {
            listener.onMessage(connection, message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Object message) throws IOException {
        channel.send(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() throws Exception {
        while (!channel.isClosed()) {
            Object message = channel.receive();
            onMessage(this, message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exception(Exception e) {
        try {
            close();
        } catch (Exception ignore) {}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        channel.close();
        onConnectionClose(this);
    }
}
