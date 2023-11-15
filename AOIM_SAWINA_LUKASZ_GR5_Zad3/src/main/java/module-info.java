module com.example.aoim_sawina_lukasz_gr5_zad3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.aoim_sawina_lukasz_gr5_zad3 to javafx.fxml;
    exports com.example.aoim_sawina_lukasz_gr5_zad3;
}