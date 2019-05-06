package ru.avalon.java.net;

import java.io.Closeable;
import java.io.IOException;

/**
 * Абстрактное представление о сетевом соединении.
 *
 * @author Daniel Alpatov
 */
public interface Connection extends Closeable {

    /**
     * Интерфейс, описывающий модель событий, отражающую
     * изменения состояния соединения.
     */
    interface StateListener {

        /**
         * Событие, которое инициируется экземпляром соединения
         * в случае, когда соединение было закрыто по тем или
         * иным причинам.
         *
         * @param connection экземпляр соединения, ставшего
         *                   источником события.
         */
        default void onConnectionClose(Connection connection) {}
    }

    /**
     * Интерфейс, описывающий модель событий получения входящих
     * сообщений в рамках установленного соединения.
     *
     * @param <E> тип, описывающий передаваемые в рамках соедининия
     *           сообщения.
     */
    interface MessageListener<E> {

        /**
         * Событие получения входящего сообщения.
         *
         * @param connection соединение, через которое сообщение
         *                   было получено.
         * @param message объект, описывающий сообщение.
         */
        default void onMessage(Connection connection, E message) {}
    }

    /**
     * Добавляет к экземпляру соединения слушатель моедли событий
     * изменения состояний соединения.
     *
     * @param listener случатель изменения состояний.
     */
    void addStateListener(StateListener listener);

    /**
     * Добавляет к экземпляру соединения слушатель модели событий
     * получения входящих сообщений.
     *
     * @param listener слушатель модели событий.
     */
    void addMessageListener(MessageListener listener);

    /**
     * Снимает обработчик модели событий изменения состояния
     * соединения.
     *
     * @param listener слушатель модели событий.
     */
    void removeStateListener(StateListener listener);

    /**
     * Снимает обработчик модели событий получения входящиях
     * сообщений.
     *
     * @param listener слушатель модели событий.
     */
    void removeMessageListener(MessageListener listener);

    /**
     * Отправляет исходящее сообщение через установленное соединение.
     *
     * @param message объект сообщения.
     *
     * @throws IOException
     */
    void send(Object message) throws IOException;

}
