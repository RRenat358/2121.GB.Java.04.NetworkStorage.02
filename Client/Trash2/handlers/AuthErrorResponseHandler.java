package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.controllers.Controller;
import ru.rrenat358.messages.auth.AuthErrorResponse;

@Log4j2
public class AuthErrorResponseHandler implements ClientRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    String reason = ((AuthErrorResponse) msg).getReason();

    log.info("Authorization failed: " + reason);

    Platform.runLater(() -> controller.showAuthMessage(reason, Color.RED));
  }
}
