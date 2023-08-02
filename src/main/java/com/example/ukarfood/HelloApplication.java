package com.example.ukarfood;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
public class HelloApplication extends Application {
    @Override

    public void start(Stage stage) throws IOException {
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }
    public void setIcon(Stage stage) throws IOException {
        Image Icon = new Image(getClass().getResource("/images/icons8-fast-food-38.png").openStream());

        stage.getIcons().add(Icon);
    }
    public static void main(String[] args) {
        launch();
    }
}