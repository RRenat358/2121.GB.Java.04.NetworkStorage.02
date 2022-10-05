package ru.rrenat358.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ru.rrenat358.network.ClientNetwork;

import java.io.IOException;

public class MainController {

    @FXML
    public Button sendButton;
    public TextField pathField;
    public TableView filesTable;



    private ClientNetwork clientNetwork = new ClientNetwork();

    public void sendButton(ActionEvent actionEvent) throws IOException, InterruptedException {
        System.out.println("sendButton >>> pressed");
        clientNetwork.sendFile();


    }



    public TextField getPathField() {
        return pathField;
    }

    public void setPathField(TextField pathField) {
        this.pathField = pathField;
    }








}
