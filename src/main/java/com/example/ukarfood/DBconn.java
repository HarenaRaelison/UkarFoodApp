package com.example.ukarfood;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBconn {
    String url = "jdbc:mysql://localhost:3308/ukarfood?characterEncoding=UTF-8";
    String user="root";
    String mdp="";
    Connection Conn;
    public void Connect(){

        try {
            Conn = DriverManager.getConnection(url,user,mdp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void rechercheUser(TextField userRecherche, Text textValue) throws SQLException {
        Conn = DriverManager.getConnection(url,user,mdp);
        String txtRecherche = userRecherche.getText();
        String query = "select password from user where username = ?";
       PreparedStatement statement = Conn.prepareStatement(query);
       statement.setString(1,txtRecherche);
       ResultSet resultSet = statement.executeQuery();
       if (resultSet.next()){
           textValue.setText("Votre mot de passe est :  "+resultSet.getString("password"));
       }else{
           textValue.setText("Le nom de l'utilisateur : "+txtRecherche+" n'est pas reconnu.");
       }
    }
    public void CheckLogin(TextField userField, PasswordField passField,Stage p) throws SQLException {
        try {
            Conn = DriverManager.getConnection(url,user,mdp);
            String Usertext=userField.getText();
            String Password=passField.getText();

            String sql = "select username,password from user where username = ? and password = ?";
            PreparedStatement statement = Conn.prepareStatement(sql);
            statement.setString(1,Usertext);
            statement.setString(2,Password);
            ResultSet resultSet = statement.executeQuery();
if (Usertext.isEmpty() || Password.isEmpty()){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText("REMPLIR LES CHAMPS");
    alert.showAndWait();
    System.out.println("champs vide");
}else{
    if (resultSet.next()){
        Stage primaryStage = new Stage();

        Menu menu = new Menu();
        menu.start(primaryStage);

System.out.println("Connecté...");
        p.close();



    }else{
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Identifiants invalides");
        alert.showAndWait();
        System.out.println("Identifiants invalides");
        userField.setText("");
        passField.setText("");



    }
}

        } catch(Exception e) {
            throw new RuntimeException(e);
        }



    }

}





