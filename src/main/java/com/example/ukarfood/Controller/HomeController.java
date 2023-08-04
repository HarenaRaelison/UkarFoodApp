package com.example.ukarfood.Controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.sql.*;

public class HomeController {
    @FXML
    private Text nombre_users;
    @FXML
    private Text depense;
    @FXML
    private Text transports;
    String url = "jdbc:mysql://localhost:3308/ukarfood?characterEncoding=UTF-8";
    String user = "root";
    String mdp = "";
    public void initialize() throws SQLException {
        nombre_user(nombre_users);
    }
    public void nombre_user(Text number) throws SQLException {
        Connection Conn = DriverManager.getConnection(url,user,mdp);
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
