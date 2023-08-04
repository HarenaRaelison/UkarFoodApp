package com.example.ukarfood.Controller;

import com.example.ukarfood.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class AchatController {
    String url = "jdbc:mysql://localhost:3308/ukarfood?characterEncoding=UTF-8";
    String user = "root";
    String mdp = "";
    @FXML private ComboBox FrnsNameBox;@FXML private ComboBox IngrNameBox;
@FXML private TextField qte;
@FXML private TextField Prix;
@FXML private Text total;
    public void BoxlistFrns() throws SQLException {
        Connection Conn = DriverManager.getConnection(url, user, mdp);
        String query = "SELECT Nom_Frns from fournisseur";
        PreparedStatement statement = Conn.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            FrnsNameBox.getItems().add(res.getString("Nom_Frns"));
        }
    }
    public void BoxIngrList() throws SQLException {
        Connection Conn = DriverManager.getConnection(url, user, mdp);
        String query = "SELECT nom_ingr from ingredients";
        PreparedStatement statement = Conn.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            IngrNameBox.getItems().add(res.getString("nom_ingr"));
        }
    }
    public void TotalsetText() throws SQLException {
      if(qte.getText().isEmpty() || Prix.getText().isEmpty()){
          total.setText("0.0 Ariary");
      }else{
          int totaux = Integer.parseInt(Prix.getText())*Integer.parseInt(qte.getText());
          total.setText(totaux +" Ariary");
      }
    }

    public void FenetreFrnsAjout() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/Ajoutmodale.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        HelloApplication obj = new HelloApplication();
        obj.setIcon(stage);


    }

}
