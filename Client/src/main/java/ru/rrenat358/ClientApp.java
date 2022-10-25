package ru.rrenat358;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.Config.ConfigConst;

@Log4j2
public class ClientApp extends Application {



    @Getter
    private static Stage clientStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.clientStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setLocation(ClientApp.class.getResource("/ru.rrenat358/mainWindow.fxml"));
        fxmlLoader.setLocation(ClientApp.class.getResource("/ru.rrenat358/client-view.fxml"));

        Parent scene = fxmlLoader.load();
        clientStage.setScene(new Scene(scene, 640, 480));
        clientStage.setTitle(ConfigConst.CLIENT_TITLE);


        clientStage.show();
    }

    public static void main(String[] args) {
        ConfigConst.logStartApp();
        launch();
    }



}
