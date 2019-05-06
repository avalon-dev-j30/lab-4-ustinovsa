package ru.avalon.java.net.messaging;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Реализация канала обмена сообщениями через сетевое соединение,
 * установленное по протоколу TCP.
 *
 * @author Daniel Alpatov
 */
class MessagingChannelImpl implements MessagingChannel {

    /**
     * Сокет, описывающий канал связи.
     */
    private final Socket socket;

    /**
     * Поток вывода объектов.
     * <p>
     * Создаётся на основе {@code socket.getOutputStream()}.
     */
    private final ObjectOutputStream out;

    /**
     * Поток ввода объектов.
     * <p>
     * Создаётся на основе {@code socket.getInputStream()}.
     */
    private final ObjectInputStream in;

    /**
     * Инициализирует экземпляр канала обмена сообщениями
     * на основе открытого сокета.
     *
     * @param socket сокет, описывающий открытое сетевое соединение.
     *
     * @throws IOException, если сокет закрыт или возникли
     * исключения в нижележащих потоковых объектах.
     */
    MessagingChannelImpl(Socket socket) throws IOException {
        if (socket.isClosed()) {
            throw new IOException("Underlying socket is closed!");
        }
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Object message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object receive() throws IOException {
        try {
            return in.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
