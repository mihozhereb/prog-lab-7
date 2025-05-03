package ru.mihozhereb.io;

/**
 * Basic interface for IO tools
 *
 * @param <T>
 */
public interface IOWorker<T> extends AutoCloseable {
    void write(T row);

    T read();

    boolean ready();

    void close();
}
