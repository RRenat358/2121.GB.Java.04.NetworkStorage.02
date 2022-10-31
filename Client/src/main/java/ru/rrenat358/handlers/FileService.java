package ru.rrenat358.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.file.FileMessage;
import ru.rrenat358.controllers.MainController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

@Log4j2
public class FileService {

  public static void sendFile(Channel channel, File file, String path, MainController mainController) {
    new Thread(() -> {
      try {
        int bufSize = 1024 * 1024 * 5;
        int partsCount = new Long(file.length() / bufSize).intValue();
        if (file.length() % bufSize != 0) {
          partsCount++;
        }

        FileMessage fileMessage = new FileMessage(file.getName(), path,-1, partsCount, new byte[bufSize]);
        FileInputStream in = new FileInputStream(file);
//        Platform.runLater(() -> mainController.setStatusProgressBar("Upload file: " + fileMessage.filename));

        for (int i = 0; i < partsCount; i++) {
          int readedBytes = in.read(fileMessage.data);
          fileMessage.partNumber = i + 1;
          if (readedBytes < bufSize) {
            fileMessage.data = Arrays.copyOfRange(fileMessage.data, 0, readedBytes);
          }
          ChannelFuture f = channel.writeAndFlush(fileMessage);
          f.sync();

//          Platform.runLater(() -> mainController.changeProgressBar((double) fileMessage.partNumber * ((double) 1 / fileMessage.partsCount)));

          log.info("File " + fileMessage.filename + " part " + (i + 1) + "/" + partsCount + " sent");
        }

//        Platform.runLater(() -> mainController.setStatusProgressBar("File " + fileMessage.filename + " is completely uploaded"));

        in.close();
      } catch (IOException | InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }
}
