package ru.mihozhereb.command;

import ru.mihozhereb.control.Handler;
import ru.mihozhereb.control.Request;
import ru.mihozhereb.control.Response;
import ru.mihozhereb.control.UDPClient;
import ru.mihozhereb.io.ConsoleWorker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

public class ExecuteScriptCommand implements Command {
    private static int recursionCounter = 0;

    @Override
    public Response execute(Request r) {
        InputStream originalSystemIn = System.in;

        recursionCounter++;

        if (recursionCounter > 5) {
            return new Response("Error. Recursion limit", null);
        }

        FileInputStream fis;
        try {
            fis = new FileInputStream(r.argument());
        } catch (FileNotFoundException e) {
            return new Response("Error. File not found", null);
        }

        System.setIn(fis);

        Handler handler;
        try {
            handler = new Handler(new UDPClient("localhost", 6666));
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        ConsoleWorker consoleWorker = new ConsoleWorker();

        while (consoleWorker.ready()) {
            String line = consoleWorker.read();
            consoleWorker.writeLn(line);
            try {
                consoleWorker.write(handler.handle(line, consoleWorker));
            } catch (IOException e) {
                consoleWorker.writeLn("Connection error. Retry later...");
            }
        }
        try {
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setIn(originalSystemIn);
        recursionCounter--;

        return new Response("Done.", null);
    }

    @Override
    public String getHelp() {
        return "execute_script file_name | read and execute a script from a file";
    }
}
