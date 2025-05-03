package ru.mihozhereb.collection;

import org.apache.commons.codec.digest.DigestUtils;

import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.io.FileWorker;
import ru.mihozhereb.io.JsonWorker;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.logging.Logger;

/**
 * CollectionManager singleton class
 */
public final class CollectionManager {
    private static final TreeSet<MusicBand> COLLECTION = new TreeSet<>();
    private static String path;
    private static final LocalDateTime CREATION_DATE_TIME = LocalDateTime.now();
    private final static Logger LOGGER = Logger.getLogger(CollectionManager.class.getName());

    private CollectionManager() {  }

    private static CollectionManager instance;

    /**
     * Return instance of {@code CollectionManager}
     *
     * @return CollectionManager instance
     */
    public static CollectionManager getInstance() {
        if (instance == null) {
            instance = new CollectionManager();
        }
        return instance;
    }

    /**
     * Return collection of {@code MusicBand}
     *
     * @return MusicBand's collection
     */
    public TreeSet<MusicBand> getCollection() {
        return COLLECTION;
    }

    /**
     * Load collection from file
     */
    public void load() throws StorageBrokenException, StorageIsNullException {
        try (JsonWorker storage = new JsonWorker(path);
             FileWorker hashStorage = new FileWorker("STORAGE_HASH", true)) {
            if (storage.ready() && hashStorage.ready()) {
                MusicBand[] storageInner = storage.read();

                if (storageInner == null) {
                    return;
                }

                String fileHash = DigestUtils.sha256Hex(Arrays.toString(storageInner) + "MY SUPER SECRET PEPPER");

                if (!Objects.equals(hashStorage.read(), fileHash)) {
                    throw new StorageBrokenException("Collection's storage broken");
                }

                COLLECTION.addAll(List.of(storageInner));
            } else {
                throw new StorageIsNullException("Collection's storage or hash file is not ready");
            }
        }
        LOGGER.info("Storage loaded");
    }

    /**
     * Save collection in file (json format)
     */
    public void save() {
        try (JsonWorker storage = new JsonWorker(path);
             FileWorker hashStorage = new FileWorker("STORAGE_HASH", false)) {
            storage.write(COLLECTION.toArray(new MusicBand[0]));
            hashStorage.write(DigestUtils.sha256Hex(COLLECTION + "MY SUPER SECRET PEPPER"));
        }
        LOGGER.info("Storage saved");
    }

    /**
     * Set new path to back up file
     * @param newPath new path
     */
    public void setPath(String newPath) {
        path = newPath;
    }

    /**
     * Return date and time of init {@code CollectionManager}
     *
     * @return CREATION_DATE_TIME
     */
    public LocalDateTime getCreationDateTime() {
        return CREATION_DATE_TIME;
    }

    public int getLastIdInCollection() {
        return COLLECTION.stream().mapToInt(MusicBand::getId).max().orElse(0);
    }
}
