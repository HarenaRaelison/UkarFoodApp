package com.example.ukarfood.Controller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.text.Text;

import java.sql.*;

public class HomeController {
    @FXML
    private Text nombre_users;
    @FXML
    private Text depense;
    @FXML
    private Text transports;
    @FXML
    private AreaChart<String, Number> areaChart;
    @FXML
    private BarChart <String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;


    @FXML
    private NumberAxis yAxis;
    String url = "jdbc:mysql://192.168.88.16:3308/ukarfood?characterEncoding=UTF-8";
    String user = "Harena";  // Utilisateur que vous avez créé
    String mdp = "passe0123";  // Mot de passe de l'utilisateur que vous avez créé

    Connection Conn = DriverManager.getConnection(url,user,mdp);

    public HomeController() throws SQLException {
    }

    public void initialize() throws SQLException {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        String req ="SELECT nom_ingr, SUM(quantite) AS total_quantite FROM ingredients " +
                "JOIN achat ON ingredients.id_ingr = achat.id_ingredient " +
                "GROUP BY nom_ingr";
        String req1 = "SELECT nom_ingr,MAX(prix_total) AS total_prix FROM ingredients  "+
                "JOIN achat ON ingredients.id_ingr = achat.id_ingredient " +
                "GROUP BY nom_ingr";

        PreparedStatement statement1=Conn.prepareStatement(req1);
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        ResultSet res1 = statement1.executeQuery();
        while(res.next() && res1.next()){
            series.getData().add(new XYChart.Data<>(res.getString("nom_ingr"), res.getInt("total_quantite")));
            series1.getData().add(new XYChart.Data<>(res1.getString("nom_ingr"), res1.getInt("total_prix")));
        }
        areaChart.getData().add(series);
        barChart.getData().add(series1);
        nombre_user(nombre_users);
    }
    public void nombre_user(Text number) throws SQLException {

        String req = "select count(*) from user";
        PreparedStatement statement = Conn.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        int Count;
        while (res.next()){
            Count = res.getInt("count(*)");
            number.setText(String.valueOf(Count));

        }
    }

}
