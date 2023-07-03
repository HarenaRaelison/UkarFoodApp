package com.example.ukarfood;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class PassOublierController {
    @FXML
    private TextField Recherche;

    @FXML
    private Text txtValue;

    public void rechercheUser() throws SQLException {
        DBconn obj = new DBconn();
        obj.rechercheUser(Recherche,txtValue);
    }
    public void BackLogin() throws IOException {

        HelloApplication Login = new HelloApplication();
        Stage stage =new Stage();
        Stage CloseStage = (Stage) Recherche.getScene().getWindow();
        CloseStage.close();
        Login.start(stage);
    }



}
