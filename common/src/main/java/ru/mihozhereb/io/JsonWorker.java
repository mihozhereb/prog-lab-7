package ru.mihozhereb.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.io.adapters.LocalDateAdapter;
import ru.mihozhereb.io.adapters.LocalDateTimeAdapter;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * JsonParser for storage file with MusicBands objects in json format
 */
public class JsonWorker implements IOWorker<MusicBand[]> {
    private final String path;
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public JsonWorker(String path) {
        this.path = path;
    }

    /**
     * @param elements Collection of MusicBand's elements
     * @see MusicBand
     */
    @Override
    public void write(MusicBand[] elements) {
        try (FileWorker fileWorker = new FileWorker(path, false)) {
            JsonBase base = new JsonBase(elements);
            fileWorker.write(GSON.toJson(base));
        }
    }

    /**
     * @return Collection of MusicBands
     */
    @Override
    public MusicBand[] read() {
        try (FileWorker fileWorker = new FileWorker(path, true)) {
            return GSON.fromJson(fileWorker.readAll(), JsonBase.class).elements();
        }
    }

    /**
     * @return Good or bad json file
     */
    @Override
    public boolean ready() {
        File file = new File(path);
        return !file.isDirectory() && file.exists() && file.canRead() && file.canWrite();
    }

    /**
     * Do nothing
     */
    @Override
    public void close() {

    }

    /**
     * Generate string of beauty json data
     *
     * @param elements List of {@code MusicBand} elements
     * @return Beauty string
     */
    public static String toJsonFormat(List<MusicBand> elements) {
        JsonBase base = new JsonBase(elements.toArray(new MusicBand[]{}));
        return GSON.toJson(base);
    }
}
