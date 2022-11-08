package ru.rrenat358.actions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.ClientApplication;
import ru.rrenat358.controllers.Controller;
import ru.rrenat358.service.FileService;
import ru.rrenat358.list.FileInfo;
import ru.rrenat358.network.NettyClient;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Log4j2
public class Upload {

  public static void action(Controller controller) {
    FileChooser fileChooser = controller.getFileChooser();
    TableView<FileInfo> filesTable = controller.getFilesTable();
    TextField pathField = controller.getPathField();
    NettyClient nettyClient = controller.getNettyClient();

    File file = fileChooser.showOpenDialog(ClientApplication.getPrimaryStage());

    if (file != null) {
      List<FileInfo> list = filesTable.getItems();
      for (FileInfo fi : list) {
        if (file.getName().equals(fi.getFileName())) {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "File already exists, overwrite?");
          Optional<ButtonType> option = alert.showAndWait();

          if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
              log.info("File chosen: " + file.getPath());
              controller.showProgressBar();
              FileService.sendFile(nettyClient.getChannelFuture().channel(), file, pathField.getText(), controller);
              return;
            }
            if (option.get() == ButtonType.CANCEL) {
              return;
            }
          }
        }
      }

      log.info("File chosen: " + file.getPath());
      controller.showProgressBar();
      FileService.sendFile(nettyClient.getChannelFuture().channel(), file, pathField.getText(), controller);
    }
  }
}
