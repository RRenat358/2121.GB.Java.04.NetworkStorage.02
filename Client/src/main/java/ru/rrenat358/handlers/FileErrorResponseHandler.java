package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import ru.rrenat358.Controller;
import ru.rrenat358.file.FileErrorResponse;

public class FileErrorResponseHandler implements ClientRequestHandler{

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    FileErrorResponse fileErrorResponse = (FileErrorResponse) msg;

    String reason = fileErrorResponse.getReason();

    Platform.runLater(() -> {
      Alert alert = new Alert(AlertType.WARNING, reason, ButtonType.OK);
      alert.showAndWait();
    });
  }
}
