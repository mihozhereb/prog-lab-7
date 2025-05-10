package ru.mihozhereb.collection;

import org.apache.commons.codec.digest.DigestUtils;

import ru.mihozhereb.collection.model.MusicBand;
import ru.mihozhereb.io.FileWorker;
import ru.mihozhereb.io.JsonWorker;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * CollectionManager singleton class
 */
public final class CollectionManager {
    private static final TreeSet<MusicBand> COLLECTION = new TreeSet<>();
    private static final LocalDateTime CREATION_DATE_TIME = LocalDateTime.now();
    private final static Logger LOGGER = Logger.getLogger(CollectionManager.class.getName());

    private CollectionManager() {  }

    private static CollectionManager instance;
    private DbManager db;
    private static final ReentrantLock locker = new ReentrantLock();

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
    public NavigableSet<MusicBand> getCollection() {
        return Collections.unmodifiableNavigableSet(COLLECTION);
    }

    /**
     * Load collection from file
     */
    public void load(DbManager dbm) throws SQLException {
        db = dbm;
        locker.lock();
        COLLECTION.addAll(db.selectMusicBands());
        locker.unlock();
        LOGGER.info("Storage loaded");
    }

    /**
     * Return date and time of init {@code CollectionManager}
     *
     * @return CREATION_DATE_TIME
     */
    public LocalDateTime getCreationDateTime() {
        return CREATION_DATE_TIME;
    }

    public void add(MusicBand mb) throws SQLException {
        db.insertMusicBand(mb);
        locker.lock();
        COLLECTION.clear();
        COLLECTION.addAll(db.selectMusicBands());
        locker.unlock();
    }

    public void remove(int ownerId, int MBId) throws SQLException {
        db.removeMusicBand(ownerId, MBId);
        locker.lock();
        COLLECTION.clear();
        COLLECTION.addAll(db.selectMusicBands());
        locker.unlock();
    }

    public void remove(int ownerId) throws SQLException {
        db.removeMusicBand(ownerId);
        locker.lock();
        COLLECTION.clear();
        COLLECTION.addAll(db.selectMusicBands());
        locker.unlock();
    }

    public void update(MusicBand newMB) throws SQLException {
        db.updateMusicBand(newMB);
        locker.lock();
        COLLECTION.clear();
        COLLECTION.addAll(db.selectMusicBands());
        locker.unlock();
    }
}
