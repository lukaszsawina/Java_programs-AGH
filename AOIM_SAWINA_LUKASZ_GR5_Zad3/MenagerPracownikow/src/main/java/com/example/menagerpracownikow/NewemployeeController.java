package com.example.menagerpracownikow;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class NewemployeeController implements Initializable {

    private IDataCommunicator FormularzPierwszy;

    @FXML
    private Button btnAnuluj;

    @FXML
    private Button btnDodaj;

    @FXML
    private ComboBox<EmployeeCondition> cbStatus;

    @FXML
    private TextField tfImie;

    @FXML
    private TextField tfNazwisko;

    @FXML
    private TextField tfRokUrodzenia;

    @FXML
    private TextField tfZarobki;

    @FXML
    void btnAnulujClicked(ActionEvent event) {
        Stage stage = (Stage) btnAnuluj.getScene().getWindow();

        stage.close();
    }

    @FXML
    void btnDodajClicked(ActionEvent event) {
        String imie = tfImie.getText();
        String nazwisko = tfNazwisko.getText();

        try {
            EmployeeCondition status = cbStatus.getValue();
            if(status == null)
                throw new Exception();

            int rokUrodzenia = Integer.valueOf(tfRokUrodzenia.getText());
            if(rokUrodzenia < 0)
                throw new Exception();

            double zarobki = Double.valueOf(tfZarobki.getText());
            if(zarobki < 0)
                throw new Exception();

            Employee nowy = new Employee(imie, nazwisko, status, rokUrodzenia, zarobki);

            FormularzPierwszy.przekazDanePracownika(nowy);
            Stage stage = (Stage) btnDodaj.getScene().getWindow();
            stage.close();
        }catch (Exception ex)
        {
            showError("Źle wprowadzone dane!");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbStatus.getItems().setAll(EmployeeCondition.values());
    }

    private void showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setKontrolerPierwszego(IDataCommunicator kontrolerPierwszego) {
        this.FormularzPierwszy = kontrolerPierwszego;
    }
}
