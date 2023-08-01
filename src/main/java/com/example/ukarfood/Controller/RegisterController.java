package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import com.example.ukarfood.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField confPass;
    @FXML
    private ImageView imageView;

    public void handleCreate() throws SQLException {
        DBconn obj=new DBconn();
        obj.register(username,password,confPass);
    }
    public void Fermer() throws IOException {
        Stage wind= (Stage) username.getScene().getWindow();
        wind.close();
        Stage stage = new Stage();
        HelloApplication Login = new HelloApplication();
        Login.start(stage);

    }
    public void Selectimage(){
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));

        File selectedFile = fileChooser.showOpenDialog(stage);
        Image image = new Image(selectedFile.toURI().toString());
        imageView.setImage(image);
        try(InputStream inputStream = new FileInputStream(selectedFile)) {
            inputStream.readAllBytes();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }



}
