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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static java.util.concurrent.Executors.newFixedThreadPool;

public class UDPServer implements Runnable {
    private final int port;
    private final static Logger LOGGER = Logger.getLogger(UDPServer.class.getName());

    private final ExecutorService readPool = newFixedThreadPool(10);
    private final ForkJoinPool processPool = ForkJoinPool.commonPool();
    private final ExecutorService writePool = newCachedThreadPool();

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
                                readPool.execute(new ReadData(sk));
                            } else {
                                readPool.execute(new ReadMeta(sk));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ошибка io");
        }
    }

    private record ClientData(DatagramChannel dc, SocketAddress cl, ByteBuffer bf){};

    private class ReadMeta implements Runnable {
        SelectionKey sk;
        public ReadMeta(SelectionKey sk) {this.sk = sk;}
        @Override
        public void run() {
            LOGGER.info("Read meta datagram");
            ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
            DatagramChannel dc = (DatagramChannel) sk.channel();
            SocketAddress client;
            try {
                client = dc.receive(sizeBuffer);
            } catch (IOException e) {
                LOGGER.warning("Failed to read meta datagram");
                return;
            }

            try {
                dc.register(sk.selector(), SelectionKey.OP_READ, new ClientData(dc, client, sizeBuffer));
            } catch (ClosedChannelException e) {
                LOGGER.warning("Selector is closed");
            }
        }
    }

    private class ReadData implements Runnable {
        ClientData cd;
        SelectionKey sk;
        public ReadData(SelectionKey sk) {
            this.sk = sk;
            this.cd = (ClientData) sk.attachment();
        }
        @Override
        public void run() {
            LOGGER.info("Read datagram");
            int size = cd.bf.flip().getInt();
            ByteBuffer reqBuffer = ByteBuffer.allocate(size);
            try {
                cd.dc.receive(reqBuffer);
            } catch (IOException e) {
                LOGGER.warning("Failed to read the datagram");
                return;
            }

            try {
                cd.dc.register(sk.selector(), SelectionKey.OP_READ);
            } catch (ClosedChannelException e) {
                LOGGER.warning("Selector is closed");
            }
            processPool.execute(new ProcessData(new ClientData(cd.dc, cd.cl, reqBuffer)));
        }
    }

    private class ProcessData implements Runnable {
        ClientData cd;
        public ProcessData(ClientData cd) {this.cd = cd;}
        @Override
        public void run() {
            LOGGER.info("Processing data");
            Request req;
            try {
                req = Request.fromJson(new String(cd.bf.array()));
            } catch (Exception e) {
                LOGGER.warning("Failed to parse the datagram");
                return;
            }
            Response res = Router.route(req);

            ByteBuffer resBuffer = ByteBuffer.wrap(res.toJson().getBytes());

            writePool.execute(new WriteData(new ClientData(cd.dc, cd.cl, resBuffer)));
        }
    }

    private class WriteData implements Runnable {
        ClientData cd;
        public WriteData(ClientData cd) {this.cd = cd;}
        @Override
        public void run() {
            LOGGER.info("Write data");
            ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
            try {
                cd.dc.send(sizeBuffer.putInt(cd.bf.limit()).flip(), cd.cl);
                cd.dc.send(cd.bf, cd.cl);
            } catch (IOException e) {
                LOGGER.warning("Failed to send the datagram");
            }
        }
    }
}
