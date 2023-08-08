package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class StockageController {
    @FXML private ComboBox nom_Ingr;
    @FXML private TableView Table;
    @FXML private TextField qte;
    @FXML private ComboBox typeOp;
    String url = "jdbc:mysql://localhost:3308/ukarfood?characterEncoding=UTF-8";
    String user = "root";
    String mdp = "";
    Connection Conn;

    public void initialize(){
        TableColumn <String,String> Nom = new TableColumn<>("Nom");
        TableColumn <String,String> Qte = new TableColumn<>("Quantité en stock");
        TableColumn <String,String> Prix = new TableColumn<>("Prix D'achat");
        TableColumn <String,String> DateCo = new TableColumn<>("Date Achat");

        Nom.setCellValueFactory(new PropertyValueFactory<>("nom_ingr"));
        Qte.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        Prix.setCellValueFactory(new PropertyValueFactory<>("prix_total"));
        DateCo.setCellValueFactory(new PropertyValueFactory<>("date_achat"));
        Table.getColumns().addAll(Nom,Qte,Prix,DateCo);
    }
    public void getListName() throws SQLException {
        Conn = DriverManager.getConnection(url,user,mdp);
        String req = "SELECT nom_ingr from ingredients";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            nom_Ingr.getItems().add(res.getString("nom_ingr"));
        }
    }
    public int getIdF() throws SQLException {
        int id ;
        String text = (String) nom_Ingr.getValue();
        String req = "SELECT id_ingr from ingredients where nom_ingr = ?";
        PreparedStatement statement = Conn.prepareStatement(req);
        statement.setString(1,text);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            id = res.getInt("id_ingr");
            return id;
        }

return 0;
    }

public void TableList() throws SQLException {
    TableList1(Table);
}

    public void TableList1(TableView <Object> table) throws SQLException {
        int Id = getIdF();
        if(Id==0){
            return;
        }
        else{
            String req ="SELECT achat.*, ingredients.nom_ingr FROM achat JOIN ingredients ON achat.id_ingredient = ingredients.id_ingr WHERE achat.id_ingredient = ?";
            Connection conn = DriverManager.getConnection(url, user, mdp);
            PreparedStatement statement = conn.prepareStatement(req);
            statement.setInt(1,Id);
            ResultSet res = statement.executeQuery();
            ObservableList<Object> buy = FXCollections.observableArrayList();
            while (res.next()){

                String name = res.getString("nom_ingr");
               int qte = (res.getInt("quantite"));
                int prix = (res.getInt("prix_total"));
                 String date = String.valueOf(res.getDate("date_achat"));
                DBconn.Stock stock = new DBconn.Stock( name, qte, prix, date);
                buy.add(stock);
            }
            Table.setItems(buy);
        }

    }
}
