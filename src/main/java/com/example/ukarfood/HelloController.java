package com.example.ukarfood;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML
    public void ConnClick() throws SQLException {
        Stage primaryStage = (Stage) userField.getScene().getWindow();
        System.out.println(primaryStage);
        DBconn obj = new DBconn();
        obj.CheckLogin(userField,passwordField,primaryStage);



    }
    public void ouverturePassOublier() throws IOException {
        Stage primaryStage = (Stage) userField.getScene().getWindow();
        primaryStage.close();
        PassOublier pass= new PassOublier();
        Stage stage = new Stage();
        pass.start(stage);

    }
    public void Fermer(){
        Stage primaryStage = (Stage) userField.getScene().getWindow();
        primaryStage.close();
    }


}