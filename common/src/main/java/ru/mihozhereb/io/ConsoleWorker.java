package ru.mihozhereb.io;

import java.io.*;

/**
 * ConsoleWorker class
 */
public class ConsoleWorker implements StringIOWorker {

    private final BufferedReader consoleReader;
    private final BufferedWriter consoleWriter;

    public ConsoleWorker() {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        consoleWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    /**
     * Write new row to the console
     *
     * @param row New row
     */
    @Override
    public void write(String row) {
        try {
            consoleWriter.write(row);
            consoleWriter.flush();
        } catch (IOException ignored) {

        }
    }

    /**
     * Read one row from the file
     *
     * @return String from the console
     */
    @Override
    public String read() {
        try {
            return consoleReader.readLine();
        } catch (IOException ignored) {

        }
        return "";
    }

    /**
     * Return ready status of consoleReader
     *
     * @return Ready or not to be read, and false, if throw IOException
     */
    @Override
    public boolean ready() {
        try {
            return consoleReader.ready();
        } catch (IOException ignored) {

        }
        return false;
    }

    /**
     * Close reader and writer connection
     */
    @Override
    public void close() {
        try {
            consoleReader.close();
            consoleWriter.close();
        } catch (IOException ignored) {

        }
    }
}
