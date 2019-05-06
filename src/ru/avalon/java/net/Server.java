package ru.avalon.java.net;

import ru.avalon.java.net.messaging.MessagingChannel;
import ru.avalon.java.threading.Asynchronous;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Абстрактное предствление об объекте, задачей которого является
 * ожидать входящие соединения.
 * <p>
 * Ожидание входящих соединений выполняется в параллельном потоке
 * исполнения.
 *
 * @author Daniel Alpatov
 */
public abstract class Server implements Asynchronous {

    /**
     * Серверный сокет.
     * <p>
     * Используется для взаимодействия с сетевым интерфейсом.
     */
    private final ServerSocket server;

    /**
     * Основной конструктор класса. Принимает порт, который будет связан
     * с соответствующим объекту серверным сокетом.
     *
     * @param port номер порта.
     *
     * @throws IOException
     */
    protected Server(int port) throws IOException {
        server = new ServerSocket(port);
    }

    /**
     * Метод жизненного цикла.
     * <p>
     * Вызывается в момент, когда входящее соединение было принято и
     * проинициализировано.
     *
     * @param connection входящее соединение.
     */
    protected void onConnectionOpened(Connection connection) {}

    /**
     * Метод жизненного цикла класса. Вызывается всякий раз, как серверный
     * сокет принимает входящее соединение.
     * <p>
     * На основе параметра {@code socket} создаётся канал обмена сообщениями,
     * который, в свою очередь, оборачивается экземпляром класса {@link ConnectionImpl}.
     *
     * @param socket сокет, описывающий входящее соединение.
     * @throws IOException
     */
    private void onSocketAccepted(final Socket socket) throws IOException {
        MessagingChannel channel = MessagingChannel.fromSocket(socket);
        ConnectionImpl connection = new ConnectionImpl(channel);
        onConnectionOpened(connection);
        connection.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() throws Exception {
        while (!server.isClosed()) {
            Socket socket = server.accept();
            onSocketAccepted(socket);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exception(Exception e) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        server.close();
    }
}
