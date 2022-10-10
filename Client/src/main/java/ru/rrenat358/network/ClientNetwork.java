package ru.rrenat358.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.Command;
import ru.rrenat358.ConfigConst;
import ru.rrenat358.controllers.MainController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Consumer;

@Log4j2
public class ClientNetwork {

    private static final String HOST = ConfigConst.HOST;
    private static final int PORT = ConfigConst.PORT;

    private static final String clientDataUserPath = ConfigConst.CLIENT_REPO;
    private static final String fileName01 = "userFile01.txt";


    private final String host;
    private final int port;

//    MainController mainController = new MainController();
    MainController mainController;

    public ClientNetwork() {
        this(HOST, PORT);
    }

    public ClientNetwork(String host, int port) {
        this.host = host;
        this.port = port;
    }



/*
    public void sendFile() throws InterruptedException, IOException {
        File file = new File(clientDataUserPath + fileName01);
        System.out.println("Файл захвачен для отправки: \n" + file.getPath());

        Command command = new Command("put", file, Files.readAllBytes(file.toPath()));

        new ClientNetwork(HOST, PORT).sendCommand(command, (respons) -> {
            System.out.println("respons = " + respons);
        });

//        mainController.getFilesTable().getItems().add(fileName01);

    }
*/

    public void sendFile(String fileName) throws InterruptedException, IOException {
        File file = new File(clientDataUserPath + fileName);
        log.debug("Файл захвачен для отправки: \n" + file.getPath());

        Command command = new Command("put", file, Files.readAllBytes(file.toPath()));

        new ClientNetwork(HOST, PORT).sendCommand(command, (respons) -> {
            log.debug("respons = " + respons);
        });
//        mainController.getFilesTable().getItems().add(fileName01);
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
                            new ClientNetworkHandler(command, callback)
                    );
                }
            });
            ChannelFuture future = client.connect(HOST, PORT).sync();
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


}

