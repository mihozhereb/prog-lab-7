package ru.mihozhereb.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.io.adapters.LocalDateAdapter;
import ru.mihozhereb.io.adapters.LocalDateTimeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Command name, optional argument, and a {@link MusicBand} object.
 *
 * @param command
 * @param argument
 * @param element
 * @see MusicBand
 */
public record Request(String command, String argument, MusicBand element, String login, String password) {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public String toJson() {
        return GSON.toJson(this);
    }

    public static Request fromJson(String src) {
        return GSON.fromJson(src, Request.class);
    }
}
