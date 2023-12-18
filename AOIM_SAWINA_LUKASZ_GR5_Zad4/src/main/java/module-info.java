module com.example.pracownicy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires com.opencsv;

    opens com.example.pracownicy;
    exports com.example.pracownicy;
}