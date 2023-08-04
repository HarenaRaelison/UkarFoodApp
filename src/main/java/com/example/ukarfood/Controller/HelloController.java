package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import com.example.ukarfood.PassOublier;
import com.example.ukarfood.Register;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    TextField userField;
    @FXML
    private PasswordField passwordField;
    @FXML

    public void ConnClick() {
        Stage primaryStage = (Stage) userField.getScene().getWindow();
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
    public void Fermer() throws IOException {
        Stage primaryStage = (Stage) userField.getScene().getWindow();
        primaryStage.close();

    }
    public void ouvertureRegister() throws IOException {
        Stage primaryStage = (Stage) userField.getScene().getWindow();
        Stage stage = new Stage();
        Register obj = new Register();
        obj.start(stage);
        primaryStage.close();
    }


}