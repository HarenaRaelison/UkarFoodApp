package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import com.example.ukarfood.HelloApplication;
import com.example.ukarfood.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class IngredientController {
    String url = "jdbc:mysql://localhost:3308/ukarfood?characterEncoding=UTF-8";
    String user = "root";
    String mdp = "";
    Connection Conn = DriverManager.getConnection(url,user,mdp);

    @FXML
    private TableView<DBconn.Ingredients> listIngre;
    @FXML
    private ComboBox <String>BarRecherche;

    public IngredientController() throws SQLException {
    }

    public void initialize() throws SQLException {
        TableColumn<DBconn.Ingredients, String> nomColumn = new TableColumn<>("Nom ingrédient");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom_ingr"));

        TableColumn<DBconn.Ingredients, String> descriColumn = new TableColumn<>("Description");
        descriColumn.setCellValueFactory(new PropertyValueFactory<>("descri"));

        listIngre.getColumns().addAll(nomColumn, descriColumn);

        refreshList();
        cate();
    }

    public void cate() throws SQLException {
        String req = "select nom_cate from categorie";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            BarRecherche.getItems().add(res.getString("nom_cate"));
        }
    }
    public int getIdCate(ComboBox<String> box) throws SQLException {
        String test = box.getValue();
        String req = "SELECT id_cate from categorie where nom_cate = ?";
        PreparedStatement statement = Conn.prepareStatement(req);
        statement.setString(1,test);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            return res.getInt("id_cate");
        }
        return 0;
    }

    public void Actualiser(MouseEvent mouseEvent) throws SQLException {
        refreshList();
    }

    public void AjoutClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("AjoutIngre.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
        HelloApplication obj = new HelloApplication();
        obj.setIcon(primaryStage);
    }


    public void RechercheFrns() throws SQLException {
        int id = getIdCate(BarRecherche);
        if(id == 0){

        }else{
            String req = "SELECT * FROM ingredients WHERE id_cate = ?";
            PreparedStatement statement = Conn.prepareStatement(req);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            ObservableList<DBconn.Ingredients> ingredients = FXCollections.observableArrayList();
            while (res.next()){
                DBconn.Ingredients ingredient = new DBconn.Ingredients(res.getString("nom_ingr"), res.getString("description"));
                ingredients.add(ingredient);
            }
            listIngre.setItems(ingredients);
        }

    }

    public void refreshList() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, mdp);
        String req = "SELECT * FROM ingredients";

        PreparedStatement statement = conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        ObservableList<DBconn.Ingredients> ingredients = FXCollections.observableArrayList();
        while (res.next()) {
            String nom = res.getString("nom_ingr");
            String description = res.getString("description");

            DBconn.Ingredients ingredient = new DBconn.Ingredients(nom, description);
            ingredients.add(ingredient);
        }
        listIngre.setItems(ingredients);
    }
}
