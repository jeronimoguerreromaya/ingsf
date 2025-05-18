module com.example.ingsf {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ingsf to javafx.fxml;
    exports com.example.ingsf;
    opens modelo.ingsf to javafx.base;
}