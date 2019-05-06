package ru.avalon.java.net.messaging;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * Абстрактное представление о канале обмена сообщениями.
 *
 * @param <E> тип, описывающий передаваемые в рамках канала сообщения.
 *
 * @author Daniel Alpatov
 */
public interface MessagingChannel<E> extends Closeable {

    /**
     * Отправляет сообщение получателю.
     *
     * @param message объект, описывающий сообщение.
     *
     * @throws IOException
     */
    void send(E message) throws IOException;

    /**
     * Возвращает следующее сообщение из канала обмена сообщениями.
     *
     * @return объект, описывающий сообщение.
     *
     * @throws IOException
     */
    E receive() throws IOException;

    /**
     * Возвращает состояние канала передачи сообщений.
     *
     * @return {@code true}, если канал закрыт. В обратном случае {@code false}.
     */
    boolean isClosed();

    /**
     * Создаёт канал обмена сообщениями на основе сетевого соединения.
     *
     * @param socket сокет, описывающий открытое сетевое соединение.
     *
     * @return экземпляр типа {@link MessagingChannel}.
     *
     * @throws IOException
     */
    static MessagingChannel fromSocket(Socket socket) throws IOException {
        return new MessagingChannelImpl(socket);
    }

}
