package com.example.menagerpracownikow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class HelloController {

    private ClassEmployee _pracownicy = new ClassEmployee();

    @FXML
    private Button btnNowaGrupa;

    @FXML
    private Button btnNowaOsoba;

    @FXML
    private TableView<?> tvGrupyPracownikow;

    @FXML
    private TableView<Employee> tvListaPracownikow;

    @FXML
    private TableColumn<Employee, String> tcImie;

    @FXML
    private TableColumn<Employee, String> tcNazwisko;

    @FXML
    private TableColumn<Employee, EmployeeCondition> tcStan;

    @FXML
    private TableColumn<Employee, Integer> tcRokUr;

    @FXML
    private TableColumn<Employee, Double> tcPensja;



    @FXML
    void btnNowaGrupaClicked(ActionEvent event) {

    }

    @FXML
    void btnNowaOsobaClicked(ActionEvent event) {
        tcImie.setCellValueFactory(new PropertyValueFactory<Employee, String>("imie"));
        tcNazwisko.setCellValueFactory(new PropertyValueFactory<Employee, String>("nazwisko"));
        tcStan.setCellValueFactory(new PropertyValueFactory<Employee, EmployeeCondition>("stanPracownika"));
        tcRokUr.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("rok_urodzenia"));
        tcPensja.setCellValueFactory(new PropertyValueFactory<Employee, Double>("wynagrodzenie"));

        Employee nowyPracownik = new Employee("Jan", "Kowalski", EmployeeCondition.obecny, 2001, 23);
        _pracownicy.addEmployee(nowyPracownik);

        List<Employee> test =  _pracownicy.getPracownicy();

        // Pobierz dane z listy pracowników
        ObservableList<Employee> data = FXCollections.observableArrayList(test);

        // Ustaw dane w tabeli
        tvListaPracownikow.setItems(data);
    }


}
