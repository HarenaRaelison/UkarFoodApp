package com.example.ukarfood.Controller;

import com.example.ukarfood.DBconn;
import com.example.ukarfood.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;

public class FournisseurController {
    @FXML
    public TableView listView;
    @FXML private TextField BarRecherche;

    String url = "jdbc:mysql://localhost:3308/ukarfood?characterEncoding=UTF-8";
    String user = "root";
    String mdp = "";
    @FXML
    public void initialize() throws SQLException {
        TableColumn<String, String> nomColumn = new TableColumn<>("Nom");
        TableColumn<String, String> adresseColumn = new TableColumn<>("Adresse");
        TableColumn<String, String> telColumn = new TableColumn<>("Téléphone");
        // Associer les noms de propriétés des objets à afficher dans chaque colonne
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Ajouter les colonnes à la TableView
        listView.getColumns().addAll(nomColumn, adresseColumn, telColumn);
        DBconn obj = new DBconn();
        obj.ActualisationList(listView);
    }

    public void AjoutClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("Ajoutmodale.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void Actualiser(MouseEvent mouseEvent) throws SQLException {
        DBconn obj = new DBconn();
        obj.ActualisationList(listView);
    }


    public int getIdFrns() throws SQLException {

        DBconn.Fournisseur selectedFournisseur = (DBconn.Fournisseur) listView.getSelectionModel().getSelectedItem();

        if (selectedFournisseur != null) {
            String name = selectedFournisseur.getNom();
            String query = "SELECT id_frns FROM fournisseur WHERE Nom_frns = ? ";
            Connection Conn = DriverManager.getConnection(url, user, mdp);
            PreparedStatement statement = Conn.prepareStatement(query);
            statement.setString(1, name);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int ID_frns = res.getInt("id_frns");
                return ID_frns;
            } else {
                return 0;
            }
        } else {
            return 0;
        }


    }
    public void SupprimerList() throws SQLException {
        int idFrns = getIdFrns(); // Assurez-vous que la méthode getIdFrns() retourne l'ID du fournisseur à supprimer.

        // Affiche la boîte de dialogue "Oui/Non".
        boolean confirmation = afficherConfirmation("Confirmation", "Voulez-vous vraiment supprimer ce fournisseur ?");

        if (confirmation) {
            // Remplacez les valeurs suivantes par les informations correctes de votre base de données.


            try (Connection conn = DriverManager.getConnection(url, user, mdp)) {
                String sql = "DELETE FROM fournisseur WHERE id_frns = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, idFrns);

                    int res = statement.executeUpdate();
                    if (res > 0) {
                        System.out.println("SUPPRESSION EFFECTUEE");
                        DBconn obj = new DBconn();
                        obj.ActualisationList(listView);
                    } else {
                        System.out.println("ECHEC DE SUPPRESSION");
                    }
                }
            } catch (SQLException e) {
                // Gérez l'exception selon votre cas d'utilisation (affichage d'un message d'erreur, journalisation, etc.).
                e.printStackTrace();
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }
    public boolean afficherConfirmation(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Ajoute les boutons "Oui" et "Non" à la boîte de dialogue.
        ButtonType boutonOui = new ButtonType("Oui");
        ButtonType boutonNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(boutonOui, boutonNon);

        // Affiche la boîte de dialogue et attend la réponse de l'utilisateur.
        // Le résultat est stocké dans un Optional<ButtonType>.
        // Si l'utilisateur clique sur "Oui", la méthode retournera true.
        // Sinon, elle retournera false.
        return alert.showAndWait().orElse(boutonNon) == boutonOui;
    }

    public void ModifyClick() throws SQLException,IOException {
        int Id = getIdFrns();
        if(Id == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText("!!!!!");
            alert.setContentText("veuiller selectionnez un Fournisseur!!");
        }else{
            ModifyFormController controller = new ModifyFormController();
            controller.setId(Id);
            FXMLLoader loader = new FXMLLoader(Menu.class.getResource("ModifyForm.fxml"));
            Scene scene = new Scene(loader.load());
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(scene);
            primaryStage.show();
            loader.setController(controller);
        }




}
public void RechercheFrns() throws SQLException {
if(BarRecherche.getText().equals("")){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Erreur");
    alert.setHeaderText("!!!!!");
    alert.setContentText("veuiller remplir le champs de texte pour effectuez une rechercher!!");
    DBconn obj = new DBconn();
    obj.ActualisationList(listView);
}else{
    Connection Conn = DriverManager.getConnection(url, user, mdp);
    String req = "SELECT * FROM fournisseur WHERE Nom_frns = ? ";
    PreparedStatement statement = Conn.prepareStatement(req);
    statement.setString(1,BarRecherche.getText());
    ResultSet res = statement.executeQuery();
    ObservableList<Object> fournisseurs = FXCollections.observableArrayList();
    while (res.next()) {
        String nom = res.getString("Nom_frns");
        String adresse = res.getString("adrs_frns");
        String telephone = res.getString("tel_frns");

        DBconn.Fournisseur fournisseur = new DBconn.Fournisseur(nom, adresse, telephone);
        fournisseurs.add(fournisseur);
    }
    listView.setItems(fournisseurs);
}


}
}
