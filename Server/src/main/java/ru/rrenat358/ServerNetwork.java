package ru.rrenat358;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;

import java.nio.charset.StandardCharsets;

//import static ru.rrenat358.ConfigConst.logStartApp;


@Log4j2
public class ServerNetwork {

    //    private static final String HOST = "localhost";
//    private static final int PORT = 13581;
    private static final String HOST = ConfigConst.HOST;
    private static final int PORT = ConfigConst.PORT;

    private static final String clientDataUserPath = ConfigConst.CLIENT_REPO;

    private static final int MAX_OBJECT_SIZE = ConfigConst.MAXIMUM_OBJECT_SIZE;


    private String host;
    private final int port;

    public static void main(String[] args) throws InterruptedException {
        ConfigConst.logStartApp();
        new ServerNetwork(PORT).startServer();
    }

    public ServerNetwork() {
        this(HOST, PORT);
    }

    public ServerNetwork(int port) {
        this.port = port;
    }

    public ServerNetwork(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void startServer() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workGroup);
            server.channel(NioServerSocketChannel.class);
            server.option(ChannelOption.SO_BACKLOG, 128);
            server.childOption(ChannelOption.SO_KEEPALIVE, true);
            server.childHandler(new ChannelInitializer<>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
//                    Object StandardCharsets = null;
                    channel.pipeline().addLast(
                            new StringEncoder(StandardCharsets.UTF_8),
                            new ObjectDecoder(MAX_OBJECT_SIZE, ClassResolvers.cacheDisabled(null)),
                            new ServerNetworkHandler()
                    );
                }
            });
            log.debug("Server start. PORT:{}", PORT);

            ChannelFuture channelFuture = server.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

/*
    public static void logStartApp() {
        log.fatal("\n");
        log.fatal("==============================");
        log.fatal("=== Start App ================");
        log.fatal("==============================");
        log.log(Level.FATAL,"LogLevel == FATAL");
        log.log(Level.ERROR,"LogLevel == ERROR");
        log.log(Level.INFO,"LogLevel == INFO");
        log.log(Level.DEBUG,"LogLevel == DEBUG");
        log.log(Level.TRACE,"LogLevel == TRACE");
        log.log(Level.ALL,"LogLevel == ALL");
        log.log(Level.FATAL,"LogLevel ==  ↑ ");
        log.fatal("==============================\n");
    }
*/



}