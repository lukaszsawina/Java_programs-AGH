package com.example.pracownicy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PracownicyApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PracownicyApplication.class.getResource("pracownicy-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PRACOWNICY");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBManager dbManager = DBManager.getInstance();
        launch();
    }
}