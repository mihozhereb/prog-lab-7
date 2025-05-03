package ru.mihozhereb.control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.io.adapters.LocalDateAdapter;
import ru.mihozhereb.io.adapters.LocalDateTimeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response text and a list of {@link MusicBand} objects.
 *
 * @param response
 * @param elements
 */
public record Response(String response, List<MusicBand> elements) {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public String toJson() {
        return GSON.toJson(this);
    }

    public static Response fromJson(String src) {
        return GSON.fromJson(src, Response.class);
    }
}
