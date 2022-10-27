package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import ru.geekbrains.cloud.common.constants.Const;
import ru.rrenat358.file.DeleteRequest;
import ru.rrenat358.file.FileErrorResponse;
import ru.rrenat358.service.ClientService;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public class DeleteRequestHandler implements ServerRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, ClientService clientService) {
    if (!clientService.isAuthorized()) {
      return;
    }

    DeleteRequest deleteRequest = (DeleteRequest) msg;

    String fileName = deleteRequest.getFileName();
    String path = deleteRequest.getPath();

    Path absolutePath = Paths.get(Const.SERVER_REP, path, fileName).toAbsolutePath();
    File file = new File(absolutePath.toString());

    if (file.delete()) {
      log.info(ctx.name() + "- File " + file.getPath() + " deleted");
      clientService.sendList(ctx, path);
    } else {
      String reason;

      if (isDirectoryEmpty(file)) {
        reason = "Delete file error";
      } else {
        reason = "Directory is not empty";
      }

      log.warn(ctx.name() + "- Not deleted: " + reason);
      ctx.writeAndFlush(new FileErrorResponse(reason));
    }
  }

  public boolean isDirectoryEmpty(File directory) {
    String[] files = directory.list();
    return files.length == 0;
  }
}
