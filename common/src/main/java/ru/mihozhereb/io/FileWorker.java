package ru.mihozhereb.io;

import java.io.*;

/**
 * FileWorker class
 */
public class FileWorker implements StringIOWorker {

    private final BufferedReader fileReader;
    private final OutputStreamWriter fileWriter;

    /**
     * FileWorker class constructor
     *
     * @param path path to the file
     * @param appendMode append or write in clear file
     */
    public FileWorker(String path, boolean appendMode) {
        try {
            new File(path).createNewFile();
            fileReader = new BufferedReader(new FileReader(path));
            fileWriter = new OutputStreamWriter(new FileOutputStream(path, appendMode));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write new row to the file
     *
     * @param row New row
     */
    @Override
    public void write(String row) {
        try {
            fileWriter.write(row);
            fileWriter.flush();
        } catch (IOException ignored) {

        }
    }

    /**
     * Read one row from the file
     *
     * @return String from the file
     */
    @Override
    public String read() {
        try {
            return fileReader.readLine();
        } catch (IOException ignored) {

        }
        return "";
    }

    public String readAll() {
        StringBuilder result = new StringBuilder();

        while (ready()) {
            result.append(read()).append(System.lineSeparator());
        }

        return result.toString();
    }

    /**
     * Return ready status of fileReader
     *
     * @return Ready or not to be read, and false, if throw IOException
     */
    @Override
    public boolean ready() {
        try {
            return fileReader.ready();
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
            fileReader.close();
            fileWriter.close();
        } catch (IOException ignored) {

        }
    }
}
