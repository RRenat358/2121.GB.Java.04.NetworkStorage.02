package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import ru.rrenat358.controllers.MainController;

public interface ClientRequestHandler {

  void handle(ChannelHandlerContext ctx, Object msg, MainController mainController);

}
