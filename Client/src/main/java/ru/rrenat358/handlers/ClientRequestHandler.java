package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import ru.rrenat358.controllers.Controller;

public interface ClientRequestHandler {

//  void handle(ChannelHandlerContext ctx, Object msg, MainController mainController);

    void handle(ChannelHandlerContext ctx, Object msg, Controller controller);
}
