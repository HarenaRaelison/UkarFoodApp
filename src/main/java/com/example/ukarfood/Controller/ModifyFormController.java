package com.example.ukarfood.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
public class ModifyFormController {
    @FXML private TextField Nom_frns;
    @FXML private TextField adress_frns;
    @FXML private TextField tel;
    @FXML private Button BtnModifier;
    static int Id;
    public void setId(int id) throws SQLException {
        this.Id = id;
    }

    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé



    @FXML
    public void RemplirChaine() throws SQLException {
        BtnModifier.setDisable(false);
System.out.println(Id);
        Connection Conn = DriverManager.getConnection(url, user, mdp);
        String query = "select Nom_frns, adrs_frns, tel_frns from fournisseur where id_frns = ?";

        PreparedStatement statement = Conn.prepareStatement(query);
        statement.setInt(1, Id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            String nom_frns = res.getString("Nom_frns");
            String adrs_frns = res.getString("adrs_frns");
            String tel_frns = res.getString("tel_frns");

            // Mettre à jour les champs de texte avec les valeurs récupérées
            Nom_frns.setText(nom_frns);
            adress_frns.setText(adrs_frns);
            tel.setText(tel_frns);
        }

    }
    public void ModifierList() throws SQLException {
        Connection Conn = DriverManager.getConnection(url,user,mdp);
        String Update  ="UPDATE fournisseur SET Nom_frns = ? ,adrs_frns = ?,tel_frns = ? WHERE id_frns = ?";

        PreparedStatement statement = Conn.prepareStatement(Update);
        statement.setString(1,Nom_frns.getText());
        statement.setString(2,adress_frns.getText());
        statement.setString(3, tel.getText());
        statement.setInt(4,Id);
        int res = statement.executeUpdate();
        if (res > 0) {
            System.out.println("Mise à jour réussie !");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");

            alert.setContentText("Modification Réussi!!!");

        } else {
            System.out.println("Aucun enregistrement mis à jour.");
        }

        Conn.close();
    }

    public void Fermer() {
        Stage stage = (Stage) Nom_frns.getScene().getWindow();
        stage.close();
    }

    // Vous pouvez implémenter les autres méthodes (ModifierList et Fermer) ici.
}
