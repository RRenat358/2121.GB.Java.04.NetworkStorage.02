package ru.rrenat358.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import ru.rrenat358.handlers.ClientHandlerRegistry;
import ru.rrenat358.handlers.ClientRequestHandler;
import ru.rrenat358.Controller;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

  private final Controller controller;

  public NettyClientHandler(Controller controller) {
    this.controller = controller;
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    ClientRequestHandler handler = ClientHandlerRegistry.getHandler(msg.getClass());
    handler.handle(ctx, msg, controller);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
