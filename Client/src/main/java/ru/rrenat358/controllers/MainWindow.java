package ru.rrenat358.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.rrenat358.Client;

import java.io.IOException;

public class MainWindow {

    @FXML
    public Button sendButton;

    private Client client = new Client();

    public void sendButton(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("sendButton >>> pressed");
        client.sendFile();


    }
}
