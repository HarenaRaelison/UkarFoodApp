package com.example.ukarfood.Controller;

import com.example.ukarfood.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class PassOublierController {
    @FXML
    private TextField Recherche;
    @FXML
    private Button btnConf;

    @FXML
    private Text txtValue;
    @FXML
    private TextField motCle;
    @FXML
    private  TextField newmdp;
    @FXML
    private  TextField conf;

    @FXML private Label motcleError;
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé

    Connection Conn = DriverManager.getConnection(url,user,mdp);

    public PassOublierController() throws SQLException {
    }


    public void rechercheUser() throws SQLException {
String nom = Recherche.getText();
        String query = "select username from user";
        PreparedStatement statement = Conn.prepareStatement(query);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            if(res.getString("username").equals(nom)){
                txtValue.setText("Username est bien dans la base de donné");
                motCle.setDisable(false);

            }
            else{
                txtValue.setText("Username n'est pas dans la base de donné");
            }
        }
    }
    public void BackLogin() throws IOException {

        HelloApplication Login = new HelloApplication();
        Stage stage =new Stage();
        Stage CloseStage = (Stage) Recherche.getScene().getWindow();
        CloseStage.close();
        Login.start(stage);
    }


    public void Verifie(MouseEvent mouseEvent) throws SQLException {
        String charGet = motCle.getText(); 
        String req = "select mot_oublier from user";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while(res.next()){
            if(!charGet.equals(res.getString("mot_oublier"))){
                motcleError.setText("Mot clé incorrecte");
                motcleError.setStyle("-fx-color-label-visible: red");
                
            }else{
                newmdp.setDisable(false);
                conf.setDisable(false);
                btnConf.setDisable(false);
                motcleError.setText("OK!");
                motcleError.setStyle("-fx-color-label-visible: green;");
            }
        }
    }

    public void Confirmation(ActionEvent actionEvent) throws SQLException {
        if(newmdp.getText().isEmpty()||conf.getText().isEmpty()){
            StockageController obj = new StockageController();
            obj.showInfoAlert("Error","Veuillez remplir les champs!!!");
        }else if(!(newmdp.getText().equals(conf.getText()))){
            StockageController obj = new StockageController();
            obj.showInfoAlert("Error","les deux champs ne se ressemble pas!!!");
        }else{
            String req = "update user set password = ? where username = ?";
            PreparedStatement statement = Conn.prepareStatement(req);
            statement.setString(1,newmdp.getText());
            statement.setString(2,Recherche.getText());
            int upt = statement.executeUpdate();
            if (upt !=0){
                StockageController obj = new StockageController();
                obj.showInfoAlert("Success","OK!!!");
                Recherche.setText("");

            }else{
                StockageController obj = new StockageController();
                obj.showInfoAlert("Error","Erreur de l'enregistrement!!!");
            }
        }
    }
}
