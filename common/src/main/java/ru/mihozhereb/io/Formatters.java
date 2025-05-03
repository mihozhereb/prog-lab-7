package ru.mihozhereb.io;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Enum of DateTimeFormatter patterns
 */
public enum Formatters {
    DATETIME(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.getDefault())),
    DATE(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.getDefault()));

    private final DateTimeFormatter formatter;

    Formatters(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    /**
     * Get formatter
     *
     * @return DateTimeFormatter
     */
    public DateTimeFormatter get() {
        return formatter;
    }
}
