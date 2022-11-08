package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import ru.rrenat358.service.ClientService;

public interface ServerRequestHandler {

  void handle(ChannelHandlerContext ctx, Object msg, ClientService clientService);

}
