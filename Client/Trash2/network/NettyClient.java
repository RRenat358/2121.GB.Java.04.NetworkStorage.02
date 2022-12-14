package ru.rrenat358.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.controllers.Controller;
import ru.rrenat358.config.ConfigConst;
import ru.rrenat358.abstractClass.AbstractMessage;

import java.util.concurrent.CountDownLatch;

@Log4j2
public class NettyClient implements Runnable{

  @Getter
  private ChannelFuture channelFuture;

  private final Controller controller;
  private final CountDownLatch countDownLatch;

  public NettyClient(Controller controller, CountDownLatch countDownLatch) {
    this.controller = controller;
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
                  new NettyClientHandler(controller));
            }
          });
      channelFuture = b.connect(ConfigConst.SERVER_REPO, ConfigConst.PORT).sync();
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





}
