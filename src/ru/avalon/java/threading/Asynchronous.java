package ru.avalon.java.threading;

import java.io.Closeable;

/**
 * Абстрактное представление об асинхронном процессе.
 * <p>
 * Асинхронный процесс понимается как некоторая сущьность, выполняемая
 * в отдельном потоке исполнения.
 * <p>
 * Поток испоолнения запускается на основе метода {@code run}
 * класса {@link Asynchronous}.
 * <p>
 * Класс обладает некоторыми преимуществами в сравнении с библиотечным
 * типом {@link Runnable}:
 * <ul>
 *     <li> Поддерживает раздельную обработку исключений. Метод {@code run}
 *          позволяет порбрасывать исключения наружу. Обработка возникающих
 *          исключений делигируется методу {@code exception}.
 *     <li> Является закрываемой сущьностью. Реализует интерфейс {@link Closeable}.
 * </ul>
 *
 * @author Daniel Alpatov
 */
public interface Asynchronous extends Closeable {

    /**
     * Потоковый метод.
     * <p>
     * Является точкой входа в поток исполнения, запущенный
     * на основе текущего экхемпляра класса.
     *
     * @throws Exception
     */
    void run() throws Exception;

    /**
     * Обработчик исключений, позникающих в методе {@code run}.
     *
     * @param e экземпляр исключения, возникшего в процессе исполнения
     *          метода {@code run} текущего экземпляра.
     */
    default void exception(Exception e) {
        throw new IllegalStateException("Asynchronous task has been terminated because of unhandled exception!", e);
    }

    /**
     * Запускает экземпляр типа {@link Asynchronous} на исполнение в
     * отдельном потоке исполнения.
     */
    default void start() {
        RunnableWrapper wrapper = new RunnableWrapper(this);
        Thread thread = new Thread(wrapper);
        thread.start();
    }

}
