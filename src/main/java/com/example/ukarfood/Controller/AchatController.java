package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import com.example.ukarfood.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;


public class AchatController {
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé

    Connection Conn = DriverManager.getConnection(url,user,mdp);

    @FXML private ComboBox<String> FrnsNameBox;
    @FXML private ComboBox<String> IngrNameBox;
    @FXML private TextField qte;
    @FXML private TextField Prix;
    @FXML private Text total;
    @FXML private DatePicker dateAchat;
    @FXML private TableView listView;


    public AchatController() throws SQLException {

    }

    public void initialize() throws SQLException {

        TableColumn<DBconn.Achat, String> frnsColumn = new TableColumn<>("Fournisseur");
        frnsColumn.setCellValueFactory(new PropertyValueFactory<>("id_frns"));

        TableColumn<DBconn.Achat, String> ingrColumn = new TableColumn<>("Ingredient");
        ingrColumn.setCellValueFactory(new PropertyValueFactory<>("id_ingr"));

        TableColumn<DBconn.Achat, String> qteColumn = new TableColumn<>("Quantité");
        qteColumn.setCellValueFactory(new PropertyValueFactory<>("qte"));

        TableColumn<DBconn.Achat, String> prixColumn = new TableColumn<>("Prix total");
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        TableColumn<DBconn.Achat, String> dateColumn = new TableColumn<>("Date d'achat");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        listView.getColumns().addAll(frnsColumn, ingrColumn, qteColumn, prixColumn, dateColumn);
        DBconn obj = new DBconn();
        obj.List(listView);

    }

    public void BoxlistFrns() throws SQLException {
        String query = "SELECT Nom_Frns from fournisseur";
        PreparedStatement statement = Conn.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            FrnsNameBox.getItems().add(res.getString("Nom_Frns"));
        }
    }

    public void BoxIngrList() throws SQLException {
        String query = "SELECT nom_ingr from ingredients";
        PreparedStatement statement = Conn.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            IngrNameBox.getItems().add(res.getString("nom_ingr"));
        }
    }

    public void TotalsetText() throws SQLException {
        if (qte.getText().isEmpty() || Prix.getText().isEmpty()) {
            total.setText("0.0 Ariary");
        } else {
            int totaux = Integer.parseInt(Prix.getText()) * Integer.parseInt(qte.getText());
            total.setText(totaux + " Ariary");
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

    public int getIdfrns(ComboBox<String> box) throws SQLException {
        String req = "SELECT id_frns from fournisseur where Nom_frns = ?";
        PreparedStatement statement = Conn.prepareStatement(req);
        statement.setString(1, box.getValue());
        ResultSet res = statement.executeQuery();

        if (res.next()) {
            int id = res.getInt("id_frns");
            return id;
        } else {
            return -1;
        }
    }

    public int getIdIngr(ComboBox<String> box) throws SQLException {
        String req = "SELECT id_ingr from ingredients where nom_ingr = ?";
        PreparedStatement statement = Conn.prepareStatement(req);
        statement.setString(1, box.getValue());
        ResultSet res = statement.executeQuery();

        if (res.next()) {
            int id = res.getInt("id_ingr");
            return id;
        } else {
            return -1;
        }
    }

    public void btnEnregistrementAchat() throws SQLException {
        String fournisseur = FrnsNameBox.getValue();
        String ingredient = IngrNameBox.getValue();
        String quantite = qte.getText();
        String prix = Prix.getText();
        LocalDate date = dateAchat.getValue();
        String tot = total.getText();
        String[] tt = tot.split(" ");

        if (fournisseur == null || fournisseur.isEmpty() || ingredient == null || ingredient.isEmpty() || quantite.isEmpty() || prix.isEmpty() || date == null) {
            // Affichez un message d'erreur ou traitez le cas où les champs sont vides
            System.out.println("Veuillez remplir tous les champs.");
        } else {
            String query = "INSERT INTO achat (id_fournisseur, id_ingredient, quantite, prix_total, date_achat) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = Conn.prepareStatement(query);
            statement.setInt(1, getIdfrns(FrnsNameBox));
            statement.setInt(2, getIdIngr(IngrNameBox));
            statement.setInt(3, Integer.parseInt(qte.getText()));
            statement.setInt(4, Integer.parseInt(tt[0]));
            statement.setDate(5, Date.valueOf(date));

            int res = statement.executeUpdate();
            if (res != 0) {
                System.out.println("Succès");
            } else {
                System.out.println("Erreur");
            }
        }
    }


    public void FenetreCate(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ukarfood/AjoutIngre.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        HelloApplication obj = new HelloApplication();
        obj.setIcon(stage);
    }
}
