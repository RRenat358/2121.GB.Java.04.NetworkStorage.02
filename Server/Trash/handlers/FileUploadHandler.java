package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.config.ConfigConst;
import ru.rrenat358.file.FileMessage;
import ru.rrenat358.service.ClientService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Log4j2
public class FileUploadHandler implements ServerRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, ClientService clientService) {
    if (!clientService.isAuthorized()) {
      return;
    }

    FileMessage fileMessage = (FileMessage) msg;

    boolean append = fileMessage.partNumber != 1;

    try (FileOutputStream fos = new FileOutputStream(Paths.get(ConfigConst.SERVER_REPO, fileMessage.path, fileMessage.filename).toString(), append)) {

      log.info(ctx.name() + ": File " + fileMessage.filename + " part " + fileMessage.partNumber + " / " + fileMessage.partsCount + " received");
      fos.write(fileMessage.data);

      if (fileMessage.partNumber == fileMessage.partsCount) {
        log.info(ctx.name() + ": File " + fileMessage.filename + " is completely uploaded");
        clientService.sendList(ctx, fileMessage.path);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
