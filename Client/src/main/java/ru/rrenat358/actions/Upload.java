package ru.rrenat358.actions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j2;
import ru.geekbrains.cloud.client.javafx.ClientApplication;
import ru.geekbrains.cloud.client.javafx.Controller;
import ru.geekbrains.cloud.client.netty.NettyClient;
import ru.geekbrains.cloud.client.service.FileService;
import ru.geekbrains.cloud.common.messages.list.FileInfo;
import ru.rrenat358.controllers.MainController;
import ru.rrenat358.network.ClientNetwork;
import ru.rrenat358.ClientApp;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Log4j2
public class Upload {

  public static void action(MainController mainController) {
    FileChooser fileChooser = mainController.getFileChooser();
//    TableView<FileInfo> filesTable = mainController.getFilesTable();
    TextField pathField = mainController.getPathField();
    ClientNetwork clientNetwork = mainController.getClientNetwork();

    File file = fileChooser.showOpenDialog(ClientApp.getClientStage());

    if (file != null) {

      List<FileInfo> list = filesTable.getItems();
      for (FileInfo fi : list) {
        if (file.getName().equals(fi.getFileName())) {
          Alert alert = new Alert(AlertType.CONFIRMATION, "File already exists, overwrite?");
          Optional<ButtonType> option = alert.showAndWait();

          if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
              log.info("File chosen: " + file.getPath());
//              mainController.showProgressBar();
              FileService.sendFile(clientNetwork.getChannelFuture().channel(), file, pathField.getText(), mainController);
              return;
            }
            if (option.get() == ButtonType.CANCEL) {
              return;
            }
          }
        }
      }

      log.info("File chosen: " + file.getPath());
//      mainController.showProgressBar();
      FileService.sendFile(clientNetwork.getChannelFuture().channel(), file, pathField.getText(), mainController);
    }
  }
}
