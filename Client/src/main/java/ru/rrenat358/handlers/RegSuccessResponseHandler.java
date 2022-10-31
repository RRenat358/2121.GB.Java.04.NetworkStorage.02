package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.Controller;

@Log4j2
public class RegSuccessResponseHandler implements ClientRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    log.info("Registration completed successfully");

    Platform.runLater(() -> controller.showRegMessage("Registration completed", Color.ROYALBLUE));
  }
}
