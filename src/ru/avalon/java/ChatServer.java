package ru.avalon.java;

import ru.avalon.java.net.Connection;
import ru.avalon.java.net.Server;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import static java.util.Collections.synchronizedCollection;

/**
 * @author Daniel Alpatov
 */
public class ChatServer extends Server implements Connection.StateListener, Connection.MessageListener<String> {

    public static void main(String[] args) throws IOException {
        try (ChatServer server = new ChatServer()) {
            server.start();
            System.out.println("Server started! To exit press ENTER >>");
            System.in.read();
        }
    }

    private final Collection<Connection> connections;

    private ChatServer() throws IOException {
        super(1001);
        connections = synchronizedCollection(new HashSet<>());
    }

    @Override
    protected void onConnectionOpened(Connection connection) {
        connection.addStateListener(this);
        connection.addMessageListener(this);
        connections.add(connection);
    }

    @Override
    public void onConnectionClose(Connection connection) {
        connection.removeStateListener(this);
        connection.removeMessageListener(this);
        connections.remove(connection);
    }

    @Override
    public void onMessage(Connection source, String message) {
        for (Connection connection : connections) {
            if (!connection.equals(source)) {
                try {
                    connection.send(message);
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
