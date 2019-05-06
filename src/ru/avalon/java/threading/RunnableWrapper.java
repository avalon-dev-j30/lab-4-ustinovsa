package ru.avalon.java.threading;

/**
 * Потоковый объект, который используется для оборачивания
 * экземпляорв типа {@link Asynchronous} с тем, чтобы их
 * можно было передать в поток исполнения.
 *
 * @author Daniel Alpatov
 */
class RunnableWrapper implements Runnable {

    /**
     * Оборачиваемый экземпляр типа {@link Asynchronous}.
     */
    private final Asynchronous target;

    RunnableWrapper(Asynchronous target) {
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            target.run();
        } catch (Exception e) {
            target.exception(e);
        }
    }

}
