package ru.rrenat358.actions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.ClientApplication;
import ru.rrenat358.controllers.Controller;
import ru.rrenat358.network.NettyClient;
import ru.rrenat358.file.MakeDirRequest;

import java.nio.file.Paths;

@Log4j2
public class MakeDirectory {

  public static void action(Controller controller) {
    TextField pathField = controller.getPathField();
    NettyClient nettyClient = controller.getNettyClient();

    Label secondLabel = new Label("Enter directory name:");

    TextField textField = new TextField();
    textField.setPrefWidth(150);

    Button btnCreate = new Button("Create");
    btnCreate.setPrefSize(80, 30);
    Button btnCancel = new Button("Cancel");

    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_RIGHT);
    hBox.setSpacing(10);
    hBox.getChildren().addAll(btnCreate, btnCancel);

    VBox vBox = new VBox();
    vBox.setSpacing(10);
    vBox.setPadding(new Insets(20));
    vBox.getChildren().addAll(secondLabel, textField, hBox);

    Scene secondScene = new Scene(vBox, 350, 125);

    Stage newWindow = new Stage();
    newWindow.setResizable(false);
    newWindow.setTitle("Make directory");
    newWindow.setScene(secondScene);
    newWindow.setX(ClientApplication.getPrimaryStage().getX() + 200);
    newWindow.setY(ClientApplication.getPrimaryStage().getY() + 200);

    newWindow.show();

    btnCreate.setOnAction(event -> {
      String fileName = textField.getText();
      String path = pathField.getText();

      nettyClient.send(new MakeDirRequest(fileName, path));
      log.info("MakeDirRequest sent: " + Paths.get(path, fileName));

      newWindow.close();
    });

    btnCancel.setOnAction(event -> newWindow.close());
  }

}
