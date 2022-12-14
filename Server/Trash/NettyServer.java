package ru.rrenat358;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.config.ConfigConst;
import ru.rrenat358.service.AuthService;

@Log4j2
public class NettyServer {

  private static final AuthService authService = new AuthService();
  private ChannelFuture channelFuture;

  public static AuthService getAuthService() {
    return authService;
  }

  public void start() throws Exception {
    log.info("Server started");

    authService.start();

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class)
          .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
              socketChannel.pipeline().addLast(
                  new ObjectDecoder(ConfigConst.MAXIMUM_OBJECT_SIZE, ClassResolvers.cacheDisabled(null)),
                  new ObjectEncoder(),
                  new NettyServerHandler()
              );
            }
          });
      channelFuture = b.bind(ConfigConst.PORT).sync();
      channelFuture.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public void stop()
  {
    channelFuture.channel().close();
    authService.stop();
  }

  public static void main(String[] args) throws Exception {
    new NettyServer().start();
  }

}
