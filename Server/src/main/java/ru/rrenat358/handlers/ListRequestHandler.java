package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.list.ListRequest;
import ru.rrenat358.service.ClientService;

@Log4j2
public class ListRequestHandler implements ServerRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, ClientService clientService) {
    if (!clientService.isAuthorized()) {
      return;
    }
    
    ListRequest listRequest = (ListRequest) msg;

    String path = listRequest.getPath();

    clientService.sendList(ctx, path);
  }
}
