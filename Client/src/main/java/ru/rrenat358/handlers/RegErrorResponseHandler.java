package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.Controller;
import ru.rrenat358.reg.RegErrorResponse;

@Log4j2
public class RegErrorResponseHandler implements ClientRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    String reason = ((RegErrorResponse) msg).getReason();

    log.info("Registrarion failed: " + reason);

    Platform.runLater(() -> controller.showRegMessage(reason, Color.RED));
  }
}
