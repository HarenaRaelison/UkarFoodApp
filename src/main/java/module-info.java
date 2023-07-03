module com.example.ukarfood {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.ukarfood to javafx.fxml;
    exports com.example.ukarfood;
} 