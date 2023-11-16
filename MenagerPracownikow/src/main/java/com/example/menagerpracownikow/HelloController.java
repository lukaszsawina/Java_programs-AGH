package com.example.menagerpracownikow;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.Map;

public class HelloController {

    private ClassEmployee _pracownicy = new ClassEmployee();

    private ClassContainer _grupy = new ClassContainer();

    @FXML
    private Button btnNowaGrupa;

    @FXML
    private Button btnNowaOsoba;

    @FXML
    private TableView<Map.Entry<String, ClassEmployee>> tvGrupyPracownikow;

    @FXML
    private TableColumn<Map.Entry<String, ClassEmployee>, String> tcZapelnienie;

    @FXML
    private TableColumn<Map.Entry<String, ClassEmployee>, String> tcNazwaGrupy;

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
        _grupy.addClass("Siema1", 2);
        _grupy.addClass("Siema2", 2);
        _grupy.addClass("Siema3", 2);

        ObservableList<Map.Entry<String, ClassEmployee>> data = FXCollections.observableArrayList(_grupy.getKontenery().entrySet());
        tcNazwaGrupy.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
        tcZapelnienie.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().przepelnienie() + " %"));

        tvGrupyPracownikow.setItems(data);
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
