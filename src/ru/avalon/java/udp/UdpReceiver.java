package ru.avalon.java.udp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Упражнение, на правленное на выработку умений, связанных с полученеим
 * сообщений, отправленных с использование протокола UDP.
 *
 * @author Daniel Alpatov
 */
public final class UdpReceiver {

    // 3. Порт, на который ожидается получение сообщения.
    protected final static int PORT = 25_134;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1. Формируем буффер, для хранения получаемых данных.
        final byte[] buffer = prepareBuffer();
        // 2. Формируем пакет, на основе созданного буфера.
        final DatagramPacket packet = preparePacket(buffer);
        // 3. Формируем сокет, связанный с выбранным портом.
        try (final DatagramSocket socket = prepareSocket(PORT)) {
            // 4. Получаем сообщение.
            socket.receive(packet);
            // 5. На основании данных пакета формируем текстовое сообщение.
            final String message = getMessage(packet);
        }
    }

/**
 * Возвращает буффер, который будет использован для храрнения получаемых данных.
 *
 * @return двоичный массив.
 */
private static byte[] prepareBuffer() {
        /*
         * TODO Реализовать метод prepareBuffer класса UdpReceiver
         */
        byte[] buffer = new byte[4_096];
        return buffer;
    }

    /**
     * Упаковывает переданный двоичный массив (буффер) в экземпляр типа
     * {@link DatagramPacket}.
     *
     * @param buffer буффер, который будет использован пакетом для хранения
     * получаемых данных.
     *
     * @return экземпляр типа {@link DatagramPacket}.
     */
    private static DatagramPacket preparePacket(byte[] buffer) {
        /*
         * TODO Реализовать метод preparePacket класса UdpReceiver
         */
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        return packet;
    }

    /**
     * Возвращает сокет, связанный с указанным портом.
     *
     * @param port порт.
     *
     * @return сокет.
     */
    private static DatagramSocket prepareSocket(int port) throws SocketException {
        /*
         * TODO Реализовать метод prepareSocket класса UdpReceiver
         */
        DatagramSocket socket = new DatagramSocket(port);
        return socket;
    }

    /**
     * Преобразует данные, содержащиеся в полученном пакете в текстовое
     * представление.
     *
     * @param packet пакет.
     *
     * @return строковое сообщение.
     */
    private static String getMessage(DatagramPacket packet) throws IOException, ClassNotFoundException {
        /*
         * TODO Реализовать метод getMessage класса UdpReceiver
         */

 /*try ( ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(
                        packet.getData(),
                        0,
                        packet.getLength()))) {
            String message = (String) ois.readObject();*/
        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Received msg : " + message);
        return message;
    }
}

//}
