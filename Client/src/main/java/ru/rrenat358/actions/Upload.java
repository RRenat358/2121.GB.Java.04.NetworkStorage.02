package ru.rrenat358.actions;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.ClientApp;
import ru.rrenat358.controllers.MainController;
import ru.rrenat358.handlers.FileService;
import ru.rrenat358.network.ClientNetwork;

import java.io.File;

@Log4j2
public class Upload {

  public static void action(MainController mainController) {
    FileChooser fileChooser = mainController.getFileChooser();
//    TableView<FileInfo> filesTable = mainController.getFilesTable();
    TextField pathField = mainController.getPathField();
    ClientNetwork clientNetwork = mainController.getClientNetwork();

    File file = fileChooser.showOpenDialog(ClientApp.getClientStage());

    if (file != null) {

/*
      //Проверяем, существует ли файл
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
*/

      log.info("File chosen: " + file.getPath());
//      mainController.showProgressBar();
      FileService.sendFile(clientNetwork.getChannelFuture().channel(), file, pathField.getText(), mainController);
    }
  }
}
