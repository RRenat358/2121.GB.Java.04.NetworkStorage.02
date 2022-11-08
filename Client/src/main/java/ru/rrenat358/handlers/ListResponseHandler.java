package ru.rrenat358.handlers;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.javafx.Controller;
import ru.rrenat358.messages.list.FileInfo;
import ru.rrenat358.messages.list.ListResponse;

import java.util.List;

@Log4j2
public class ListResponseHandler implements ClientRequestHandler {

  @Override
  public void handle(ChannelHandlerContext ctx, Object msg, Controller controller) {
    ListResponse listResponse = (ListResponse) msg;

    List<FileInfo> list = listResponse.getList();
    String path = listResponse.getPath();

    log.info("Received a list from the server: " + path);

    Platform.runLater(() -> {
      controller.updateList(list);
      controller.updatePathField(path);
    });
  }
}
