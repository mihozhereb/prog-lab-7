package ru.mihozhereb.collection;

import java.io.IOException;

public class StorageIsNullException extends IOException {
    public StorageIsNullException(String message) {
        super(message);
    }
}
