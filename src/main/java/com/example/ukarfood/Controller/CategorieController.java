package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class CategorieController {
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé

    Connection Conn = DriverManager.getConnection(url,user,mdp);

@FXML private TextField nom;
@FXML private TextField descri;
    public CategorieController() throws SQLException {
    }

    public void ajoutList(ActionEvent actionEvent) throws SQLException {
        if(nom.getText().isEmpty() || descri.getText().isEmpty()){
            StockageController obj = new StockageController();
            obj.showInfoAlert("Erreur","Remplir les champs!!");
        }else{
            String req = "INSERT INTO categorie (nom_cate,descri_cate) values (?,?)";
            PreparedStatement statement = Conn.prepareStatement(req);
            statement.setString(1,nom.getText());
            statement.setString(2,descri.getText());
            int res = statement.executeUpdate();
            if (res != 0 ){
                StockageController obj = new StockageController();
                obj.showInfoAlert("Succés","Ajout effectuer!!!");
                nom.setText("");
                descri.setText("");
            }
        }


    }

    public void Fermer(MouseEvent mouseEvent) {
        Stage stage = (Stage) nom.getScene().getWindow();
        DBconn obj = new DBconn();
        obj.powerOf(stage);
    }
}
