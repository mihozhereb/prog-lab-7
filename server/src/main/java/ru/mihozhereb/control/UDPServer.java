package ru.mihozhereb.control;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.Logger;

public class UDPServer implements Runnable {
    private final int port;
    private final static Logger LOGGER = Logger.getLogger(UDPServer.class.getName());

    public UDPServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (DatagramChannel dc = DatagramChannel.open();
             Selector selector = Selector.open()) {
            dc.bind(new InetSocketAddress(port));
            dc.configureBlocking(false);
            dc.register(selector, SelectionKey.OP_READ);

            LOGGER.info("Server running...");

            while (true) {
                selector.select(100);
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey sk = iterator.next();
                    iterator.remove();

                    if (sk.isValid()) {
                        if (sk.isReadable()) {
                            if (sk.attachment() != null) {
                                doReadAndWrite(sk);
                            } else {
                                doReadMeta(sk);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ошибка io");
        }
    }

    private static void doReadMeta(SelectionKey sk) {
        LOGGER.info("Read meta datagram");
        ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
        DatagramChannel dc = (DatagramChannel) sk.channel();
        try {
            dc.receive(sizeBuffer);
        } catch (IOException e) {
            LOGGER.warning("Failed to read the datagram");
            return;
        }
        try {
            dc.register(sk.selector(), SelectionKey.OP_READ, sizeBuffer.flip().getInt());
        } catch (ClosedChannelException e) {
            LOGGER.warning("Selector is closed");
        }
    }

    private static void doReadAndWrite(SelectionKey sk) {
        LOGGER.info("Read datagram");
        DatagramChannel dc = (DatagramChannel) sk.channel();
        int size = (int) sk.attachment();
        ByteBuffer reqBuffer = ByteBuffer.allocate(size);
        SocketAddress client;
        try {
            client = dc.receive(reqBuffer);
        } catch (IOException e) {
            LOGGER.warning("Failed to read the datagram");
            return;
        }

        Request req;
        try {
            req = Request.fromJson(new String(reqBuffer.array()));
        } catch (Exception e) {
            LOGGER.warning("Failed to parse the datagram");
            return;
        }
        Response res = Router.route(req);

        ByteBuffer resBuffer = ByteBuffer.wrap(res.toJson().getBytes());
        ByteBuffer sizeBuffer = ByteBuffer.allocate(4);

        try {
            dc.send(sizeBuffer.putInt(resBuffer.limit()).flip(), client);
            dc.send(resBuffer, client);
        } catch (IOException e) {
            LOGGER.warning("Failed to send the datagram");
        }

        try {
            dc.register(sk.selector(), SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            LOGGER.warning("Selector is closed");
        }
    }
}
