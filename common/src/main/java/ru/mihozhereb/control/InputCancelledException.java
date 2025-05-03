package ru.mihozhereb.control;

/**
 * An exception is raised when a user cancel console input
 */
public class InputCancelledException extends Exception {
    public InputCancelledException(String message) {
        super(message);
    }
}
