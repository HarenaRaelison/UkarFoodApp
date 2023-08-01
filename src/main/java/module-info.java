module com.example.ukarfood {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;


    opens com.example.ukarfood to javafx.fxml;
    exports com.example.ukarfood;
    exports com.example.ukarfood.Controller;
    opens com.example.ukarfood.Controller to javafx.fxml;
} 