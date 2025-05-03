package ru.mihozhereb.collection.utils;

import ru.mihozhereb.io.FileWorker;

/**
 * ID generator singleton class
 */
public final class IdGenerator {
    private IdGenerator() {  }

    private static IdGenerator instance;

    /**
     * Return instance of {@code IdGenerator}
     *
     * @return IdGenerator instance
     */
    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    /**
     * Generates a new id using the sequence saved in the file
     *
     * @return new id
     */
    public int getNewId(int lastIdInCollection) {
        String path = "ID_LOG";
        int previousId;
        try (FileWorker fileWorker = new FileWorker(path, true)) {
            previousId = Integer.parseInt(fileWorker.read());
        } catch (NumberFormatException e) {
            previousId = -1;
        }

        if (lastIdInCollection > previousId) {
            previousId = lastIdInCollection;
        }

        try (FileWorker fileWorker = new FileWorker(path, false)) {
            fileWorker.write(String.valueOf(previousId + 1));
            return previousId + 1;
        }
    }
}
