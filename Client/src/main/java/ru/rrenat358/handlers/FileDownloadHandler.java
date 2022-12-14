package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.javafx.Controller;
import ru.rrenat358.constants.Const;
import ru.rrenat358.messages.file.FileMessage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Log4j2
public class FileDownloadHandler implements ClientRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    FileMessage fileMessage = (FileMessage) msg;

    boolean append;
    if (fileMessage.partNumber == 1) {
      append = false;
      Platform.runLater(() -> controller.setStatusProgressBar("Download file: " + fileMessage.filename));
    } else {
      append = true;
    }

    try (FileOutputStream fos = new FileOutputStream(Paths.get(Const.CLIENT_REP, fileMessage.filename).toString(), append)) {
      log.info(ctx.name() + ": File " + fileMessage.filename + " part " + fileMessage.partNumber + " / " + fileMessage.partsCount + " received");
      fos.write(fileMessage.data);

      Platform.runLater(() -> controller.changeProgressBar((double) fileMessage.partNumber * ((double) 1 / fileMessage.partsCount)));

      if (fileMessage.partNumber == fileMessage.partsCount) {
        Platform.runLater(() -> controller.setStatusProgressBar("File " + fileMessage.filename + " is completely downloaded"));
        log.info(ctx.name() + ": File " + fileMessage.filename + " is completely downloaded");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
