module com.example.kursach {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.kursach to javafx.fxml;
    exports com.example.kursach;
}