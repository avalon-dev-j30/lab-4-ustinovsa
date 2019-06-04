package ru.avalon.java.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Упражнение на выаботку умений связанных с получением сообщений, отправленных
 * с использованием протокола TCP.
 *
 * @author Daniel Alpatov
 */
public final class TcpReceiver {
    // 1. Определяем порт, на котором ожидается соединение.

    protected final static int PORT = 25_133;

    public static void main(String[] args) throws IOException {
        // 2. Подготавливаем серверный сокет.
        try ( ServerSocket listener = prepareServerSocket(PORT)) {
            // 3. Принимаем соединение.
            Socket socket = listener.accept();
            // 4. Получаем сообщение.
            final String message = receive(socket);
        }
    }

    /**
     * Возвращает серверный сокет, связанный с портом, описанным параметром
     * {@code port}.
     *
     * @param port порт, на котором предполагается получать входящие соединения.
     *
     * @return серверный сокет, связанный с портом {@code port}.
     */
    private static ServerSocket prepareServerSocket(int port) {
        ServerSocket srvSocket = null;
        try {
            /*
            * TODO Реализовать метод prepareServerSocket класса TcpReceiver
             */
            srvSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            ex.getMessage();
        }

        return srvSocket;
    }

    /**
     * Возвращает сообщение, прочитанное из входящего потока, указанного сокета.
     *
     * @param socket сокет, описывающий сетевое соединение.
     *
     * @return строковое сообщение.
     */
    private static String receive(Socket socket) throws IOException {
        /*
         * TODO Реализовать метод receive класса TcpReceiver
         */
        try ( ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            String msg;
            while (true) {
                try {
                    msg = (String) ois.readObject();
                    System.out.println("Server receive new message : " + msg);
                    return msg;
                } catch (ClassNotFoundException ex) {
                        socket.close();
                        ex.getMessage();
                    }
                }
            }
        }

    }

