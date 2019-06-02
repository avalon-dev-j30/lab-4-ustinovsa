package ru.avalon.java.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Упражнение на выработку базовых умений использования протокола TCP.
 *
 * @author Daniel Alpatov
 */
public final class TcpSender {

    private static final int PORT = TcpReceiver.PORT;
    private static ObjectOutputStream oos;
    private static BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        // 1. Подготавливааем сообщение
        final String message = prepareMessage();
        // 2. Подготавливаем адрес
        final SocketAddress address = prepareAddress();
        // 4. Отправляем сообщение
        try ( // 3. Устанавливаем соединение
                 Socket socket = connect(address)) {
            // 4. Отправляем сообщение
            send(socket, message);
            // 5. Закрываем соединеие
        }
    }

    /**
     * Возвращает объект сообщения.
     *
     * @return текстовое сообщение.
     */
    private static String prepareMessage() throws IOException {
        /*
         * TODO Реализовать метод prepareMessage класса TcpSender
         */
        System.out.println("Enter new message : ");
        String message = in.readLine(); //"CLIENT send message";
        return message;
    }

    /**
     * Возвращает адрес, на который будет выполнена отправка сообщения.
     *
     * @return экземпля типа {@link SocketAddress}
     */
    private static SocketAddress prepareAddress() throws UnknownHostException {
        /*
         * TODO Реализовать метод prepareAddress класса TcpSender
         */
        return new InetSocketAddress(InetAddress.getLocalHost(), PORT);
    }

    /**
     * Устанавливает соединение на указанный адрес и возвращает сокет
     * описывающий соединение.
     *
     * @param address адрес, на который будет выполено соединение.
     *
     * @return сокет, описывающий открытое соединеие.
     *
     * @throws IOException
     */
    private static Socket connect(SocketAddress address) throws IOException {
        /*
         * TODO Реализовать метод connect класса TcpSender
         */
        Socket socket = new Socket();
        socket.connect(address);
        return socket;

    }

    /**
     * Выполняет отправку сообщения {@code message} на {@code socket}.
     *
     * @param socket соединение, через которое будет отправлено сообщение.
     * @param message сообщение
     *
     * @throws IOException
     */
    private static void send(Socket socket, String message) throws IOException {
        /*
         * TODO Реализовать метод send класса TcpSender
         */
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
    }

}
