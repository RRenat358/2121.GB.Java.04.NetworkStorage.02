package ru.rrenat358.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.abstractClass.AbstractMessage;
import ru.rrenat358.Command;
import ru.rrenat358.config.ConfigConst;
import ru.rrenat358.controllers.MainController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;


@Log4j2
@Getter
public class ClientNetwork implements Runnable {

    private static final String HOST = ConfigConst.HOST;
    private static final int PORT = ConfigConst.PORT;

    private static final String clientDataUserPath = ConfigConst.CLIENT_REPO;
    private static final String fileName01 = "userFile01.txt";


    private String host = null;
    private int port = 0;

    private ChannelFuture channelFuture;

    //    MainController mainController = new MainController();
    private MainController mainController;
    private CountDownLatch countDownLatch;


    public ClientNetwork() {
        this(HOST, PORT);
    }

    public ClientNetwork(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ClientNetwork(MainController mainController, CountDownLatch countDownLatch) {
        this.mainController = mainController;
        this.countDownLatch = countDownLatch;
    }

    public ClientNetwork(String host, int port, MainController mainController, CountDownLatch countDownLatch) {
        this.host = host;
        this.port = port;
        this.mainController = mainController;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(ConfigConst.MAXIMUM_OBJECT_SIZE, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder(),
                                    new ClientNetworkHandler(mainController));
                        }
                    });
            channelFuture = b.connect(HOST, PORT).sync();
            countDownLatch.countDown();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public void send(AbstractMessage message) {
        channelFuture.channel().writeAndFlush(message).addListener(future -> {
            if (!future.isSuccess()) {
                log.info(future.cause().getMessage());
            }
        });
    }


/*
    public void sendFile() throws InterruptedException, IOException {
        File file = new File(clientDataUserPath + fileName01);
        log.debug("Файл захвачен для отправки: \n" + file.getPath());
        Command command = new Command("put", file, Files.readAllBytes(file.toPath()));
        new ClientNetwork(HOST, PORT).sendCommand(command, (respons) -> {
            log.debug("respons = " + respons);
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
            channelFuture = client.connect(HOST, PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


}

