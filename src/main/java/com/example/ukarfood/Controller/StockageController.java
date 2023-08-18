package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

import java.sql.*;

public class StockageController {
    @FXML
    private ComboBox nom_Ingr;
    @FXML
    private TableView Table;
    @FXML
    private TextField qte;
    @FXML
    private ComboBox typeOp;
    @FXML
    private ListView listHist;
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé



    Connection Conn;

    public void initialize() throws SQLException {
        qte.setText("0");
        TableColumn<String, String> Nom = new TableColumn<>("Nom");
        TableColumn<String, String> Qte = new TableColumn<>("Quantité en stock");
        TableColumn<String, String> Prix = new TableColumn<>("Prix D'achat");
        TableColumn<String, String> DateCo = new TableColumn<>("Date Achat");

        Nom.setCellValueFactory(new PropertyValueFactory<>("nom_ingr"));
        Qte.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        Prix.setCellValueFactory(new PropertyValueFactory<>("prix_total"));
        DateCo.setCellValueFactory(new PropertyValueFactory<>("date_achat"));
        Table.getColumns().addAll(Nom, Qte, Prix, DateCo);

    }

    public void getListName() throws SQLException {
        Conn = DriverManager.getConnection(url, user, mdp);
        String req = "SELECT nom_ingr from ingredients";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            nom_Ingr.getItems().add(res.getString("nom_ingr"));
        }
    }

    public int getIdF() throws SQLException {
        int id;
        String text = (String) nom_Ingr.getValue();
        String req = "SELECT id_ingr from ingredients where nom_ingr = ?";
        PreparedStatement statement = Conn.prepareStatement(req);
        statement.setString(1, text);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            id = res.getInt("id_ingr");
            return id;
        }

        return 0;
    }

    public void setListHist() throws SQLException {
        ObservableList<String> items = FXCollections.observableArrayList();
        String req = "SELECT * FROM stock ORDER BY id_stock DESC";
        Conn = DriverManager.getConnection(url, user, mdp);
            PreparedStatement statement = Conn.prepareStatement(req);
            ResultSet res = statement.executeQuery();
            while(res.next()){

                int idIngredient = res.getInt("id_ingredient");
                int quantity = res.getInt("quantity");
                Timestamp movementDate = res.getTimestamp("movement_date");


                String entry = "Vous avez fait une operation sur ingredient numéro : " + idIngredient +
                        "de quantité : " + quantity + ", le : " + movementDate.toString();
                items.add(entry);
            }
        listHist.setItems(items);



    }
    public void TableList() throws SQLException {
        TableList1(Table);
    }

    public void TableList1(TableView<Object> table) throws SQLException {
        int Id = getIdF();
        if (Id == 0) {
            return;
        } else {
            String req = "SELECT achat.*, ingredients.nom_ingr FROM achat JOIN ingredients ON achat.id_ingredient = ingredients.id_ingr WHERE achat.id_ingredient = ?";
            Connection conn = DriverManager.getConnection(url, user, mdp);
            PreparedStatement statement = conn.prepareStatement(req);
            statement.setInt(1, Id);
            ResultSet res = statement.executeQuery();
            ObservableList<Object> buy = FXCollections.observableArrayList();
            while (res.next()) {
                int id = res.getInt("id_achat");
                String name = res.getString("nom_ingr");
                int qte = (res.getInt("quantite"));
                int prix = (res.getInt("prix_total"));
                String date = String.valueOf(res.getDate("date_achat"));
                DBconn.Stock stock = new DBconn.Stock(id, name, qte, prix, date);
                buy.add(stock);
            }
            Table.setItems(buy);
        }
    }

    public void getIdAchatIngr() {
        DBconn.Stock objSelected = (DBconn.Stock) Table.getSelectionModel().getSelectedItem();

        int nbr = Integer.parseInt(qte.getText());
        int qteselected = objSelected.getQuantite();
        int totalfn = qteselected - nbr;
        if (objSelected == null) {
            return;
        } else {
            qte.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    if (nbr < qteselected) {
                        System.out.println("opération normal");
                    }
                    if (nbr == qteselected) {
                        System.out.println("opération egaux avec suppression de row");
                    }
                    if (nbr > qteselected) {
                        System.out.println("opération multiple suppression multiple de row");
                    }

                } else {
                    System.out.println("Entrez la quantité demander");
                }
            });
        }
    }

    public void rec(int nbr, int qteselected, DBconn.Stock objSelected) throws SQLException {

        int totalfn = qteselected - nbr;
        if (nbr < qteselected) {
            String req = "UPDATE achat SET quantite = ? where id_achat = ?";
            String req1 = "INSERT INTO stock (id_ingredient,quantity,movement_type,movement_date) VALUES (?,?,?,?)";
            PreparedStatement statement1 = Conn.prepareStatement(req1);
            statement1.setInt(1,objSelected.getId());
            if(nbr<qteselected){
                statement1.setInt(2,nbr);
            }else{
                statement1.setInt(2,qteselected);
            }
            statement1.setString(3,"Out");
            statement1.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            int res1 = statement1.executeUpdate();
            if(res1!=0){
                System.out.println("mety");
            }
            else{
                System.out.println("Tsy mety");
            }
            try {

                PreparedStatement statement = Conn.prepareStatement(req);


                statement.setInt(1, totalfn);
                statement.setInt(2, objSelected.getId());
                int res = statement.executeUpdate();
                if (res != 0) {

                    showInfoAlert("Opération", "EFFECTUEZ");
                    TableList();
                    qte.setText("0");
                    setListHist();
                } else {
                    System.out.println("tsy norma");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (nbr == qteselected) {
            String req = "DELETE from achat where id_achat = ?";
            try {
                PreparedStatement statement = Conn.prepareStatement(req);

                statement.setInt(1, objSelected.getId());
                int res = statement.executeUpdate();
                if (res != 0) {
                    System.out.println("norma");
                    TableList();
                    showInfoAlert("Opération", "EFFECTUEZ");
                    setListHist();


                } else {
                    showInfoAlert("alerte !", "Echec d'enregistrement");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (nbr > qteselected) {
            int selectedIndex = Table.getItems().indexOf(objSelected);
            String req = "DELETE FROM achat where id_achat = ?";
            try {
                PreparedStatement statement = Conn.prepareStatement(req);
                statement.setInt(1, objSelected.getId());
                int res = statement.executeUpdate();
                if (res != 0) {
                    nbr -= qteselected;
                    qte.setText(String.valueOf(nbr));
                    if (selectedIndex >= 0 && selectedIndex < Table.getItems().size() - 1) {
                        Table.getSelectionModel().select(selectedIndex + 1);

                        rec(nbr, qteselected, objSelected);
                        setListHist();

                    } else {
                        showInfoAlert("alerte ", "Stock insuffisant de " + String.valueOf(nbr) + " kg");
                        TableList();
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void operation() throws SQLException {
        DBconn.Stock objSelected = (DBconn.Stock) Table.getSelectionModel().getSelectedItem();
        int nbr = Integer.parseInt(qte.getText());
        int qteselected = objSelected.getQuantite();
        if (objSelected == null) {

            showInfoAlert("Mise à jour de la quantité", "La mise à jour a été effectuée avec succès.");
        } else {

            if (qte.equals("") || qte.equals("0")){
                return;
            } else {
                rec(nbr, qteselected, objSelected);
            }

        }
    }
}
