package ru.avalon.java.model;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.getProperty;

/**
 * @author Daniel Alpatov
 */
public abstract class AbstractMessage {

    private final long time;

    private final String user;

    protected AbstractMessage() {
        time = currentTimeMillis();
        user = getProperty("user.name");
    }

    public long getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }
}
