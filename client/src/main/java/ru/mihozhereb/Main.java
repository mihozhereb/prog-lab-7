package ru.mihozhereb;

import ru.mihozhereb.control.Handler;
import ru.mihozhereb.control.UDPClient;
import ru.mihozhereb.io.ConsoleWorker;

import java.io.IOException;
import java.net.SocketException;

public final class Main {
    public static void main(final String... args) {
        new Main().run(args);
    }

    private void run(final String... args) {
        Handler handler;
        try {
            handler = new Handler(new UDPClient("localhost", 6666));
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        try (ConsoleWorker consoleWorker = new ConsoleWorker()) {
            consoleWorker.writeLn("Welcome!");
            String line;
            while ((line = consoleWorker.read()) != null && !line.equals("exit")) {
                try {
                    consoleWorker.write(handler.handle(line, consoleWorker));
                } catch (IOException e) {
                    consoleWorker.writeLn("Connection error. Retry later...");
                }

            }
        }
    }
}