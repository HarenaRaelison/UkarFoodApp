package com.example.ukarfood.Controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjoutFormController {
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé

    Connection Conn = DriverManager.getConnection(url,user,mdp);

    @FXML
    public TextField nom;
    @FXML
    public TextField adress;
    @FXML
    public TextField tel;

    private MenuController menuController;
    private FournisseurController fournisseurController;

    public AjoutFormController() throws SQLException {
    }


    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }
    @FXML
    public void ajoutList() throws SQLException {
        if(nom.getText().equals("")|| adress.getText().equals("") || tel.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur d'Ajout");
            alert.setHeaderText("!!!!!");
            alert.setContentText("Remplir tout les champs!!");


        }else{
    String name = nom.getText();
    String adresse = adress.getText();
    String tele =  tel.getText();


            String sql = "INSERT INTO fournisseur (Nom_frns,adrs_frns,tel_frns) VALUES (?,?,?)";

            PreparedStatement statement = Conn.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,adresse);
            statement.setString(3,tele);
            int res = statement.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if(res!=0){
                alert.setTitle("Ajout réussi");
                alert.setHeaderText(null);
                alert.setContentText("Le fournisseur a été ajouté avec succès!");
            }else{
                alert.setTitle("erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le fournisseur n'a pas ete ajouter");

            }
            alert.showAndWait();


        }
    }
private Parent Root;

    public void Fermer() {

        Stage text = (Stage) nom.getScene().getWindow();
        text.close();
    }
}
