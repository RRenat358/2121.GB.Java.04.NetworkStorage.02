package ru.rrenat358.actions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.controllers.Controller;
import ru.rrenat358.network.NettyClient;
import ru.rrenat358.file.DeleteRequest;
import ru.rrenat358.list.FileInfo;

import java.nio.file.Paths;

@Log4j2
public class Delete {

  public static void action(Controller controller) {
    TableView<FileInfo> filesTable = controller.getFilesTable();
    TextField pathField = controller.getPathField();
    NettyClient nettyClient = controller.getNettyClient();

    if (filesTable.getSelectionModel().getSelectedItem() == null) {
      Alert alert = new Alert(AlertType.WARNING, "File not selected", ButtonType.OK);
      alert.showAndWait();
      return;
    }

    String fileName = filesTable.getSelectionModel().getSelectedItem().getFileName();
    String path = pathField.getText();

    nettyClient.send(new DeleteRequest(fileName, path));
    log.info("DeleteRequest sent: " + Paths.get(path, fileName));
  }

}
