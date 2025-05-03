package ru.mihozhereb.collection;

import java.io.IOException;

public class StorageBrokenException extends IOException {
    public StorageBrokenException(String message) {
        super(message);
    }
}
