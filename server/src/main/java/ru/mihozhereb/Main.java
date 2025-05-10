package ru.mihozhereb;

import ru.mihozhereb.collection.CollectionManager;
import ru.mihozhereb.collection.DbManager;
import ru.mihozhereb.collection.StorageBrokenException;
import ru.mihozhereb.collection.StorageIsNullException;
import ru.mihozhereb.control.UDPServer;
import ru.mihozhereb.control.UserManager;
import ru.mihozhereb.io.ConsoleWorker;

import java.sql.SQLException;
import java.util.logging.Logger;

public final class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(final String... args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down...");
        }));

        new Main().run(args);
    }

    private void run(final String... args) {
        DbManager db;
        try {
            db = new DbManager(
                    "jdbc:postgresql://localhost:5433/studs", "s465887", "yzsxbfkR7yNekiMF"
            );
            CollectionManager.getInstance().load(db);
            UserManager.getInstance().load(db);
        } catch (SQLException e) {
            LOGGER.warning(e.getMessage());
            return;
        }

        UDPServer server = new UDPServer(6666);
        new Thread(server).start();

        try (ConsoleWorker consoleWorker = new ConsoleWorker()) {
            String line;
            while ((line = consoleWorker.read()) != null) {
                if (line.equals("exit")) {
                    System.exit(0);
                }
            }
        }
    }
}
