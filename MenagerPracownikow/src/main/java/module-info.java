module com.example.menagerpracownikow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.menagerpracownikow to javafx.fxml;
    exports com.example.menagerpracownikow;
}