package ru.rrenat358;


import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

public class ClientApp extends Application {

    @Getter
    private Stage clientStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.clientStage = stage;

    }



}
