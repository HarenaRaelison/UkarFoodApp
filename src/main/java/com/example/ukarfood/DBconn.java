package com.example.ukarfood;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class DBconn {
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";
    String mdp = "passe0123";

Connection Conn;
    public void CheckLogin(TextField userField, PasswordField passField, Stage p) {
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            Conn = DriverManager.getConnection(url, user, mdp);
            String Usertext = userField.getText();
            String Password = passField.getText();

            String sql = "select username,password from user where username = ? and password = ?";
            statement = Conn.prepareStatement(sql);
            statement.setString(1, Usertext);
            statement.setString(2, Password);
            resultSet = statement.executeQuery();
            if (Usertext.isEmpty() || Password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("REMPLIR LES CHAMPS");
                alert.showAndWait();
                System.out.println("champs vide");
            } else {
                if (resultSet.next()) {
                    Stage stage = (Stage) userField.getScene().getWindow();
                    Stage primaryStage = new Stage();
                    Menu menu = new Menu();
                    stage.close();
                    menu.start(primaryStage);
                    System.out.println("Connecté...");

                } else {
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

    public void register(TextField username, TextField password, TextField confirmePass,TextField mot_oublier){
        String Name = username.getText();
        String Pass = password.getText();
        String confPass = confirmePass.getText();
        String mot = mot_oublier.getText();



        if (Name.isEmpty() || Pass.isEmpty() || confPass.isEmpty()|| mot.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("REMPLIR LES CHAMPS");
            alert.showAndWait();
            System.out.println("Champs vide");
            return;
        }

        if (!Pass.equals(confPass)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Les mots de passe ne correspondent pas");
            alert.showAndWait();
            System.out.println("Les mots de passe ne correspondent pas");
            password.setText("");
            confirmePass.setText("");
            return;
        }

        try(Connection conn = DriverManager.getConnection(url, user, mdp);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO user (username, password,mot_oublier) VALUES (?, ?, ?)")) {

            statement.setString(1, Name);
            statement.setString(2, Pass);
            statement.setString(3,mot);


            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Row(s) affected");
                username.setText("");
                password.setText("");
                confirmePass.setText("");
                mot_oublier.setText("");
            } else {
                System.out.println("No rows affected");
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public void List(TableView<Object> tableView) throws SQLException {
        String req ="SELECT * FROM achat";
        Connection conn = DriverManager.getConnection(url, user, mdp);
             PreparedStatement statement = conn.prepareStatement(req);
             ResultSet res = statement.executeQuery();
            ObservableList<Object> buy = FXCollections.observableArrayList();
            while (res.next()){
                String id_fournisseur = String.valueOf(res.getInt("id_fournisseur"));
                String id_ingredient = String.valueOf(res.getInt("id_ingredient"));
                String qte = String.valueOf(res.getInt("quantite"));
                String prix =  String.valueOf(res.getInt("prix_total"));
                String date = String.valueOf(res.getDate("date_achat"));
                Achat achat = new Achat(id_fournisseur, id_ingredient, qte, prix, date);
                buy.add(achat);
            }
            tableView.setItems(buy);
    }
    public void ActualisationList(TableView<Object> tableView) throws SQLException {
        Connection Conn = DriverManager.getConnection(url, user, mdp);
        String req = "SELECT * FROM fournisseur";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        ObservableList<Object> fournisseurs = FXCollections.observableArrayList();
        while (res.next()){
            String nom = res.getString("Nom_frns");
            String adresse = res.getString("adrs_frns");
            String telephone = res.getString("tel_frns");
            Fournisseur fournisseur = new Fournisseur(nom, adresse, telephone);
            fournisseurs.add(fournisseur);
        }
        tableView.setItems(fournisseurs);
    }

    // Votre classe Fournisseur avec les propriétés correspondantes
    public static class Fournisseur {
        private String nom;
        private String adresse;
        private String telephone;

        public Fournisseur(String nom, String adresse, String telephone) {
            this.nom = nom;
            this.adresse = adresse;
            this.telephone = telephone;
        }

        public String getNom() {
            return nom;
        }

        public String getAdresse() {
            return adresse;
        }

        public String getTelephone() {
            return telephone;
        }
    }


    public static class Achat {
        private String id_frns;
        private String id_Ingr;
        private String qte;
        private String prix;
        private String date;

        public Achat(String id_frns, String id_Ingr, String qte,String prix,String date) {
            this.id_frns = id_frns;
            this.id_Ingr = id_Ingr;
            this.qte = qte;
            this.prix = prix;
            this.date = date;
        }

        public String getId_frns() {
            return id_frns;
        }
        public String getId_ingr() {
            return id_Ingr;
        }
        public String getQte() {return qte;}
        public String getPrix() {return prix;}

        public String getDate(){return date;}



    }


    public static class Stock {
        private int id;
        private String nom_ingr;
        private int quantite;
        private double prix_total;
        private String date_achat;

        public Stock(int id, String nom_ingr, int quantite, int prix_total, String date_achat) {
            this.id = id;
            this.nom_ingr = nom_ingr;
            this.quantite = quantite;
            this.prix_total = prix_total;
            this.date_achat = date_achat;
        }

        public int getId() {
            return id;
        }

        public String getNom_ingr() {
            return nom_ingr;
        }

        public int getQuantite() {
            return quantite;
        }

        public double getPrix_total() {
            return prix_total;
        }

        public String getDate_achat() {
            return date_achat;
        }
    }
    public void powerOf(Stage stage){
        stage.close();
    }
    public static class Ingredients {
        private String nom_ingr;
        private String descri;


        public Ingredients(String nom_ingr, String descri ) {
            this.descri = descri;
            this.nom_ingr = nom_ingr;

        }

        public String getNom_ingr() {
            return nom_ingr;
        }
        public String getDescri() {
            return descri;
        }


    }




}