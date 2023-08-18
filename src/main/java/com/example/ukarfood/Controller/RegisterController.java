package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import com.example.ukarfood.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confPass;
    @FXML private TextField motOublier;


    public void handleCreate() throws SQLException {
        DBconn obj=new DBconn();
        obj.register(username,password,confPass,motOublier);
    }
    public void Fermer() throws IOException {
        Stage wind= (Stage) username.getScene().getWindow();
        wind.close();
        Stage stage = new Stage();
        HelloApplication Login = new HelloApplication();
        Login.start(stage);

    }




}
