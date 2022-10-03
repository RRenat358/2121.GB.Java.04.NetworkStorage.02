package ru.rrenat358;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import ru.rrenat358.controllers.MainWindow;

public class ClientApp extends Application {

    @Getter
    private Stage clientStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.clientStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainWindow.class.getResource("mainWindow.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        clientStage.setScene(scene);


        clientStage.show();
    }

    public static void main(String[] args) {
        launch();
    }



}
