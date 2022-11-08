package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.javafx.Controller;
import ru.rrenat358.messages.auth.AuthSuccessResponse;

@Log4j2
public class AuthSuccessResponseHandler implements ClientRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    AuthSuccessResponse authSuccessResponse = (AuthSuccessResponse) msg;

    String login = authSuccessResponse.getLogin();

    log.info("Authorization completed successfully");

    Platform.runLater(() -> {
      controller.setLogin(login);
      controller.changeStageToCloud();
    });
  }
}
