package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.constants.Const;
import ru.rrenat358.messages.file.MakeDirRequest;
import ru.rrenat358.service.ClientService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public class MakeDirRequestHandler implements ServerRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, ClientService clientService) {
    if (!clientService.isAuthorized()) {
      return;
    }
    
    MakeDirRequest makeDirRequest = (MakeDirRequest) msg;

    String fileName = makeDirRequest.getFileName();
    String path = makeDirRequest.getPath();

    Path absolutePath = Paths.get(Const.SERVER_REP, path, fileName).toAbsolutePath();
    File file = new File(absolutePath.toString());

    boolean result = file.mkdir();
    log.info(ctx.name() + "- Make directory " + absolutePath + " result " + result);

    clientService.sendList(ctx, path);
  }
}
