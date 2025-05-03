package ru.mihozhereb.io;

public interface StringIOWorker extends IOWorker<String> {
    /**
     * Default write + line separator
     *
     * @param row String
     */
    default void writeLn(String row) {
        write(row + System.lineSeparator());
    }
}
