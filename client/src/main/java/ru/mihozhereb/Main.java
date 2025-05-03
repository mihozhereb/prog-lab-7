package ru.mihozhereb;

import ru.mihozhereb.control.Handler;
import ru.mihozhereb.control.UDPClient;
import ru.mihozhereb.control.UserData;
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
            String line;

            consoleWorker.writeLn("Welcome! Select mode: sign-in (1) or sign-up (2). Enter 1 or 2:");

            while ((line = consoleWorker.read()) != null && !line.equals("exit")) {
                if (line.equals("1")) {
                    signIn(consoleWorker);
                } else if (line.equals("2")) {
                    signUp(consoleWorker, handler.getClient());
                } else {
                    consoleWorker.writeLn("Welcome! Select mode: sign-in (1) or sign-up (2). Enter 1 or 2:");
                    continue;
                }
                break;
            }

            while ((line = consoleWorker.read()) != null && !line.equals("exit")) {
                try {
                    consoleWorker.write(handler.handle(line, consoleWorker));
                } catch (IOException e) {
                    consoleWorker.writeLn("Connection error. Retry later...");
                }

            }
        }
    }

    private void signIn(ConsoleWorker cw) {
        cw.write("Enter your login: ");
        UserData.setUserLogin(cw.read());
        cw.write("Enter your password: ");
        UserData.setUserPassword(cw.read());
        cw.writeLn("You are logged in!");
    }

    private void signUp(ConsoleWorker cw, UDPClient cl) {
        while (true) {
            cw.write("Enter your login: ");
            String login = cw.read();
            cw.write("Enter your password: ");
            String password = cw.read();
            String res = UserData.register(login, password, cl);
            if (res.equals("Done.")) {
                UserData.setUserLogin(login);
                UserData.setUserPassword(password);
                cw.writeLn("You are logged in!");
                return;
            } else {
                cw.writeLn("Error. " + res);
            }
        }
    }
}