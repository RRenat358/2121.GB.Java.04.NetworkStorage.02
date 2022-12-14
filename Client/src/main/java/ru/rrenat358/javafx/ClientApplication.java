package ru.rrenat358.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class ClientApplication extends Application {

  @Getter
  private static Stage primaryStage;

  @Override
  public void start(Stage stage) throws IOException {
    primaryStage = stage;

    FXMLLoader fxmlLoader = new FXMLLoader(ClientApplication.class.getResource("/views/client-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 720, 480);
    stage.setTitle("GeekBrains Cloud!");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}