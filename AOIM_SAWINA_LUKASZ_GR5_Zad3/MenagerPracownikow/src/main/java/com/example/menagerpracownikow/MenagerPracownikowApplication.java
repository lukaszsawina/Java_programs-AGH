package com.example.menagerpracownikow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenagerPracownikowApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenagerPracownikowApplication.class.getResource("menagerpracownikow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MenagerPracownikowController appController = fxmlLoader.getController();
        appController.initPrzyklad();
        stage.setTitle("Menadżer pracowników");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}