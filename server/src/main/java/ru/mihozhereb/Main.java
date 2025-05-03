package ru.mihozhereb;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.DbManager;
import ru.mihozhereb.collection.StorageBrokenException;
import ru.mihozhereb.collection.StorageIsNullException;
import ru.mihozhereb.control.UDPServer;
import ru.mihozhereb.io.ConsoleWorker;

import java.sql.SQLException;
import java.util.logging.Logger;

public final class Main {
    private static final String DEFAULT_STORAGE_PATH = "storage.json";
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(final String... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down...");
            CollectionManager.getInstance().save();
        }));

        new Main().run(args);
    }

    private void run(final String... args) {
        // set storagePath to file that contains collection's items and load it
        String storagePath = DEFAULT_STORAGE_PATH;
        if (args.length == 1) {
            storagePath = args[0];
        }
        CollectionManager.getInstance().setPath(storagePath);

        try {
            DbManager db = new DbManager(
                    "jdbc:postgresql://localhost:5433/studs", "s465887", "yzsxbfkR7yNekiMF"
            );
            db.SelectBands();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UDPServer server = new UDPServer(6666);
        new Thread(server).start();

        try (ConsoleWorker consoleWorker = new ConsoleWorker()) {
            try {
                CollectionManager.getInstance().load();
            } catch (StorageBrokenException e) {
                LOGGER.warning("Storage is broken, load empty collection.");
            } catch (StorageIsNullException e) {
                LOGGER.warning("Storage or hash is null, load empty collection.");
            }

            String line;
            while ((line = consoleWorker.read()) != null) {
                if (line.equals("exit")) {
                    System.exit(0);
                } else if (line.equals("save")) {
                    CollectionManager.getInstance().save();
                }
            }
        }
    }
}
