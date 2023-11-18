package com.example.menagerpracownikow;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class MenagerPracownikowController implements IDataCommunicator{

    private ClassContainer _grupy = new ClassContainer();

    private ClassEmployee _wybranaKlasa;

    private  Employee _wybranyPracownik;

    public Employee wybranyPracownik()
    {
        if(_wybranyPracownik != null)
            return _wybranyPracownik;
        else
            return null;
    }

    @FXML
    private Button btnNowaGrupa;

    @FXML
    private Button btnUsunGrupe;

    @FXML
    private Button btnNowaOsoba;

    @FXML
    private Button btnUsunOsobe;

    @FXML
    private Button btnSzukaj;

    @FXML
    private Button btnWyczysc;

    @FXML
    private Button btnEdytuj;

    @FXML
    private Button btnSortuj;

    @FXML
    private TextField tfSzukajOsoby;

    @FXML
    private Label lbListaPracownikowTitle;

    @FXML
    private TableView<Map.Entry<String, ClassEmployee>> tvGrupyPracownikow;

    @FXML
    private TableColumn<Map.Entry<String, ClassEmployee>, String> tcZapelnienie;

    @FXML
    private TableColumn<Map.Entry<String, ClassEmployee>, String> tcMax;

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
    public void initialize() {
        btnNowaOsoba.setDisable(true);
        btnUsunOsobe.setDisable(true);
        btnUsunGrupe.setDisable(true);
        btnSzukaj.setDisable(true);
        btnWyczysc.setDisable(true);
        btnEdytuj.setDisable(true);
        btnSortuj.setDisable(true);
        tfSzukajOsoby.setDisable(true);

        tvGrupyPracownikow.setRowFactory(tv -> {
            TableRow<Map.Entry<String, ClassEmployee>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Map.Entry<String, ClassEmployee> clickedItem = row.getItem();
                    String nazwa = clickedItem.getKey();
                    lbListaPracownikowTitle.setText("Lista pracowników grupy: " + nazwa);
                    _wybranaKlasa = clickedItem.getValue();

                    if(clickedItem.getValue().przepelnienie() != 100)
                        btnNowaOsoba.setDisable(false);
                    btnUsunGrupe.setDisable(false);
                    btnSzukaj.setDisable(false);
                    btnWyczysc.setDisable(false);
                    btnSortuj.setDisable(false);
                    tfSzukajOsoby.setDisable(false);
                    wyswietlPracownikow();
                }
            });
            return row;
        });

        tvListaPracownikow.setRowFactory(tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                    Employee clickedItem = row.getItem();
                    _wybranyPracownik = clickedItem;
                    btnUsunOsobe.setDisable(false);
                    btnEdytuj.setDisable(false);
                    wyswietlPracownikow();
                }
            });
            return row;
        });
    }

    @FXML
    void btnNowaGrupaClicked(ActionEvent event) {
        Stage formStage = new Stage();
        formStage.setTitle("Nowa grupa");

        try {
            FXMLLoader formLoader = new FXMLLoader(getClass().getResource("newgroupform.fxml"));
            Parent formRoot = formLoader.load();

            NewgroupController formController = formLoader.getController();

            formController.setKontrolerPierwszego(this);

            Scene formScene = new Scene(formRoot);
            formStage.setScene(formScene);

            formStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnNowaOsobaClicked(ActionEvent event) {
        Stage formStage = new Stage();
        formStage.setTitle("Nowy pracownik");

        try {
            FXMLLoader formLoader = new FXMLLoader(getClass().getResource("newemployeeform.fxml"));
            Parent formRoot = formLoader.load();

            NewemployeeController formController = formLoader.getController();
            formController.setKontrolerPierwszego(this);

            Scene formScene = new Scene(formRoot);
            formStage.setScene(formScene);

            formStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void btnUsunOsobeClicked(ActionEvent event) {
        if(_wybranyPracownik != null)
        {
            _wybranaKlasa.removeEmployee(_wybranyPracownik);
            _wybranyPracownik = null;
            btnUsunOsobe.setDisable(true);
            btnNowaOsoba.setDisable(false);
            wyswietlGrupy();
            wyswietlPracownikow();
        }
    }

    @FXML
    void btnUsunGrupeClicked(ActionEvent event) {
        if(_wybranaKlasa != null)
        {
            _grupy.removeClass(_wybranaKlasa.getNazwa());
            _wybranaKlasa = null;
            _wybranyPracownik = null;

            btnNowaOsoba.setDisable(true);
            btnUsunOsobe.setDisable(true);
            btnUsunGrupe.setDisable(true);
            btnSzukaj.setDisable(true);
            btnWyczysc.setDisable(true);
            btnEdytuj.setDisable(true);
            btnSortuj.setDisable(true);
            tfSzukajOsoby.setDisable(true);

            lbListaPracownikowTitle.setText("Lista pracowników");

            wyswietlGrupy();
            wyczyscPracownikow();
        }
    }

    @FXML
    void btnSzukajClicked(ActionEvent event) {
        List<Employee> wynik = _wybranaKlasa.searchPartial(tfSzukajOsoby.getText());
        wyswietlKonkretnychPracownikow(wynik);
    }

    @FXML
    void btnWyczyscClicked(ActionEvent event) {
        tfSzukajOsoby.setText("");
        wyswietlPracownikow();
    }

    @FXML
    void btnSortujClicked(ActionEvent event) {
        List<Employee> wynik = _wybranaKlasa.sortBySalary();
        wyswietlKonkretnychPracownikow(wynik);
    }

    @FXML
    void btnEdytujClicked(ActionEvent event) {
        Stage formStage = new Stage();
        formStage.setTitle("Edycja pracownika");

        try {
            FXMLLoader formLoader = new FXMLLoader(getClass().getResource("editemployeeform.fxml"));
            Parent formRoot = formLoader.load();

            EditemployeeController formController = formLoader.getController();
            formController.setKontrolerPierwszego(this);
            formController.init();

            Scene formScene = new Scene(formRoot);
            formStage.setScene(formScene);

            formStage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void wyswietlGrupy()
    {
        ObservableList<Map.Entry<String, ClassEmployee>> data = FXCollections.observableArrayList(_grupy.getKontenery().entrySet());
        tcNazwaGrupy.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getKey()));
        tcZapelnienie.setCellValueFactory(param -> new SimpleStringProperty(Math.round(param.getValue().getValue().przepelnienie()) + " %"));
        tcMax.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getMax())));

        tvGrupyPracownikow.setItems(data);
        tvGrupyPracownikow.refresh();
    }

    public void initPrzyklad()
    {
        _grupy.addClass("Pierwsza", 2);
        _grupy.addClass("Druga", 4);
        _grupy.addClass("Trzecia", 6);
        _grupy.addClass("Czwarta", 8);

        _grupy.addEmployeeToClass("Pierwsza" , new Employee("Jan", "Kowalski", EmployeeCondition.obecny, 2001, 12));
        _grupy.addEmployeeToClass("Druga" , new Employee("Ewa", "Nowak", EmployeeCondition.nieobecny, 1887, 32));

        wyswietlGrupy();
    }

    public void wyswietlPracownikow()
    {
        tcImie.setCellValueFactory(new PropertyValueFactory<Employee, String>("imie"));
        tcNazwisko.setCellValueFactory(new PropertyValueFactory<Employee, String>("nazwisko"));
        tcStan.setCellValueFactory(new PropertyValueFactory<Employee, EmployeeCondition>("stanPracownika"));
        tcRokUr.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("rok_urodzenia"));
        tcPensja.setCellValueFactory(new PropertyValueFactory<Employee, Double>("wynagrodzenie"));

        // Pobierz dane z listy pracowników
        ObservableList<Employee> data = FXCollections.observableArrayList(_wybranaKlasa.getPracownicy());

        // Ustaw dane w tabeli
        tvListaPracownikow.setItems(data);
        tvListaPracownikow.refresh();
    }

    public void wyswietlKonkretnychPracownikow(List<Employee> pracownicy)
    {
        tcImie.setCellValueFactory(new PropertyValueFactory<Employee, String>("imie"));
        tcNazwisko.setCellValueFactory(new PropertyValueFactory<Employee, String>("nazwisko"));
        tcStan.setCellValueFactory(new PropertyValueFactory<Employee, EmployeeCondition>("stanPracownika"));
        tcRokUr.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("rok_urodzenia"));
        tcPensja.setCellValueFactory(new PropertyValueFactory<Employee, Double>("wynagrodzenie"));

        // Pobierz dane z listy pracowników
        ObservableList<Employee> data = FXCollections.observableArrayList(pracownicy);

        // Ustaw dane w tabeli
        tvListaPracownikow.setItems(data);
        tvListaPracownikow.refresh();
    }


    public void wyczyscPracownikow()
    {
        tcImie.setCellValueFactory(new PropertyValueFactory<Employee, String>("imie"));
        tcNazwisko.setCellValueFactory(new PropertyValueFactory<Employee, String>("nazwisko"));
        tcStan.setCellValueFactory(new PropertyValueFactory<Employee, EmployeeCondition>("stanPracownika"));
        tcRokUr.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("rok_urodzenia"));
        tcPensja.setCellValueFactory(new PropertyValueFactory<Employee, Double>("wynagrodzenie"));

        // Pobierz dane z listy pracowników
        ObservableList<Employee> data = FXCollections.observableArrayList();

        // Ustaw dane w tabeli
        tvListaPracownikow.setItems(data);
        tvListaPracownikow.refresh();
    }

    @Override
    public void przekazDaneGrupy(String nazwa, int pojemnosc) {
        _grupy.addClass(nazwa, pojemnosc);
        wyswietlGrupy();
    }

    @Override
    public void przekazDanePracownika(Employee nowyPracownik) {
        _grupy.addEmployeeToClass(_wybranaKlasa.getNazwa(), nowyPracownik);

        if(_wybranaKlasa.przepelnienie() == 100)
            btnNowaOsoba.setDisable(true);

        wyswietlGrupy();
        wyswietlPracownikow();
    }

    @Override
    public void zaktualizujPracownika(EmployeeCondition nowyStatus, double noweZarobki) {
       _wybranaKlasa.changeCondition(_wybranyPracownik, nowyStatus);
       _wybranyPracownik.setWynagrodzenie(noweZarobki);
       wyswietlPracownikow();
    }

    @Override
    public boolean czyGrupaIstnieje(String nazwa) {
        return _grupy.czyIstnieje(nazwa);
    }
}
