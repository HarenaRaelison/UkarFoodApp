package com.example.ukarfood.Controller;

import com.example.ukarfood.HelloApplication;
import com.example.ukarfood.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class AjoutIngreController {
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé

    Connection Conn = DriverManager.getConnection(url,user,mdp);

    @FXML
    private TextField nom;
    @FXML
    private TextField descri;
    @FXML
    private ComboBox<String> Cate;

    public AjoutIngreController() throws SQLException {
    }

    public void listCombo() throws SQLException {
        String req = "SELECT nom_cate FROM categorie";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            Cate.getItems().add(res.getString("nom_cate"));
        }
    }
    public int getIdCate(String nom) throws SQLException {
        String req = "SELECT id_cate from categorie where nom_cate = ?";
        PreparedStatement statement = Conn.prepareStatement(req);
        statement.setString(1,nom);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            return res.getInt("id_cate");
        }

        return 0;
    }



    public void ajoutList(ActionEvent actionEvent) throws SQLException {
        int id = getIdCate(Cate.getValue());

        if(nom.getText().isEmpty() || descri.getText().isEmpty() || Cate.getValue().isEmpty()){
            StockageController obj = new StockageController();
            obj.showInfoAlert("Erreur D'Ajout","remplir les champs!!!");
        }else{
            String req = "INSERT INTO ingredients (nom_ingr,description,id_cate) VALUES (?,?,?)";
            PreparedStatement statement = Conn.prepareStatement(req);
            statement.setString(1,nom.getText());
            statement.setString(2,descri.getText());

            statement.setInt(3,id);
            int res = statement.executeUpdate();
            if(res!=0){
                StockageController obj = new StockageController();
                obj.showInfoAlert("Succés","Ajout effectuer!!!");
                nom.setText("");
                descri.setText("");
                Cate.setValue("");
            }else{
                StockageController obj = new StockageController();
                obj.showInfoAlert("Erreur","Erreur lors de l'ajout!!!");
            }
        }
    }

    public void Fermer(MouseEvent mouseEvent) {
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.close();
    }

    public void ClickBtnCate(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("Categorie.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
        HelloApplication obj = new HelloApplication();
        obj.setIcon(primaryStage);
    }
}
