package ru.rrenat358;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Consumer;


public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 13581;

    private static final String clientDataUserPath = "Client/DataUser/";
    private static final String fileName01 = "userFile01.txt";


    private final String host;
    private final int port;

    public Client() {
        this(HOST, PORT);
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        File file = new File(clientDataUserPath + fileName01);
        System.out.println("Файл захвачен для отправки: \n" + file.getPath());

        Command command = new Command("put", file, Files.readAllBytes(file.toPath()));

        new Client("localhost", 13581).sendCommand(command, (respons) -> {
            System.out.println("respons = " + respons);
        });

        while (true) {
            Scanner scanner = new Scanner(System.in);
            if (scanner.nextLine().equals("s")) {
                new Client("localhost", 13581).sendCommand(command, (response) -> {
                    System.out.println("response = " + response);
                });
                System.out.println("sss");
            }
        }

    }


    private void sendCommand(Command command, Consumer<String> callback) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(workerGroup);
            client.channel(NioSocketChannel.class);
            client.option(ChannelOption.SO_KEEPALIVE, true);
            client.handler(new ChannelInitializer<>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    channel.pipeline().addLast(
                            new ObjectEncoder(),
                            new LineBasedFrameDecoder(80),
                            new StringDecoder(StandardCharsets.UTF_8),
                            new ClientHandler(command, callback)
                    );
                }
            });
            ChannelFuture future = client.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


}

