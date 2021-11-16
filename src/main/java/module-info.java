module com.example.bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.bomberman to javafx.fxml;
    exports com.example.bomberman;
    exports com.example.bomberman.entities;
    opens com.example.bomberman.entities to javafx.fxml;
}