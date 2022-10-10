package ru.rrenat358.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import ru.rrenat358.network.ClientNetwork;

import java.io.IOException;


@Log4j2
public class MainController {

    @FXML
    public Button sendButton;
    public TextField pathField;
    public TableView filesTable;
    public ListView filesList;



    private static final String fileName01 = "userFile01.txt";



    private ClientNetwork clientNetwork = new ClientNetwork();

    public void sendButton(ActionEvent actionEvent) throws IOException, InterruptedException {
        log.debug("sendButton >>> pressed");
        clientNetwork.sendFile(fileName01);

        //todo ??
        filesTable.getItems().add(fileName01);
        filesTable.getItems().addAll(fileName01);

        //todo temp
        filesList.getItems().add(fileName01);


    }



    public TextField getPathField() {
        return pathField;
    }

    public void setPathField(TextField pathField) {
        this.pathField = pathField;
    }


    public TableView getFilesTable() {
        return filesTable;
    }

    public void setFilesTable(TableView filesTable) {
        this.filesTable = filesTable;
    }




}
