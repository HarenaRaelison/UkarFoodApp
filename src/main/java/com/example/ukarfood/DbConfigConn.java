package com.example.ukarfood;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfigConn extends Application {

    private TextField urlField;
    private TextField usernameField;
    private PasswordField passwordField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Label titleLabel = new Label("Configuration de la Base de Données");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label urlLabel = new Label("URL JDBC:");
        urlField = new TextField();

        Label usernameLabel = new Label("Nom d'utilisateur:");
        usernameField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        passwordField = new PasswordField();

        Button saveButton = new Button("Enregistrer");
        saveButton.setOnAction(e -> saveConfig());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(
                titleLabel, urlLabel, urlField,
                usernameLabel, usernameField,
                passwordLabel, passwordField,
                saveButton
        );

        Scene scene = new Scene(vbox, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Configuration de la Base de Données");
        primaryStage.show();
    }

    private void saveConfig() {
        String url = urlField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Vous pouvez stocker ces valeurs dans un fichier de configuration,
        // une base de données locale, ou dans d'autres mécanismes de stockage.

        // Pour cet exemple, nous affichons simplement les valeurs saisies.
        System.out.println("URL JDBC: " + url);
        System.out.println("Nom d'utilisateur: " + username);
        System.out.println("Mot de passe: " + password);
    }

    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/config.properties")) {
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            // Gérer l'erreur de chargement du fichier
        }
        String jdbcUrl = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        // Utilisez les valeurs chargées pour configurer votre connexion à la base de données ici
    }
}
