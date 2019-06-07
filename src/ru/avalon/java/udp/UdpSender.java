package ru.avalon.java.udp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Упражнение, направленное на выработку умений, связанных с отправкой сообщений
 * средствами протокола UDP.
 *
 * @author Daniel Alpatov
 */
public final class UdpSender {

    private final static int PORT = 25_134; //UdpReceiver.PORT;
    private static BufferedReader in
            = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        // 1. Формируем сообщение
        final String message = prepareMessage();
        // 2. Формируем адрес конечной точки.
        final SocketAddress address = prepareAddress();
        // 3. Формируем датаграмму.
        final DatagramPacket packet = pack(message);
        // 4. Устанавливаем адрес для пакета.
        packet.setSocketAddress(address);
        // 5. Создаём сокет
        try (DatagramSocket socket = createSocket()) {
            // 6. Отправляем сообщение
            socket.send(packet);
        }
    }

    /**
     * Возвращает сообщение.
     *
     * @return текстовое сообщение.
     */
    private static String prepareMessage() throws IOException {
        /*
         * TODO Реализовать метод prepareMessage класса UdpSender
         */
        System.out.println("Enter new message: ");
        String message = in.readLine();
        return message;
    }

    /**
     * Возвращает адрес конечной точки взаимодействия.
     *
     * @return адрес конечной точки.
     */
    private static SocketAddress prepareAddress() throws UnknownHostException {
        /*
         * TODO Реализовать метод prepareAddress класса UdpSender
         */
        SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), PORT);
        return address;
    }

    /**
     * Возвращает сокет, описывающий взаимодействие по протоколу UDP.
     *
     * @return сокет.
     * @throws IOException
     */
    private static DatagramSocket createSocket() throws IOException {
        /*
         * TODO Реализовать метод createSocket класса UdpSender
         */
        DatagramSocket socket = new DatagramSocket();
        return socket;
    }

    /**
     * Упаковывает текстовое сообщение в объект типа {@link DatagramPacket}.
     *
     * @param message строковое сообщение.
     *
     * @return экземпляр типа {@link DatagramPacket}.
     */
    private static DatagramPacket pack(String message) throws IOException {
        /*
         * TODO Реализовать метод pack класса UdpSender
         */
        byte[] buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        System.out.println("Packet prepared");
        return packet;
    }
}
