package com.example.menagerpracownikow;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditemployeeController {

    private Employee _pracownik;

    private IDataCommunicator FormularzPierwszy;

    @FXML
    private Button btnAnuluj;

    @FXML
    private Button btnDodaj;

    @FXML
    private ComboBox<EmployeeCondition> cbStatus;

    @FXML
    private Label lbPracownik;

    @FXML
    private TextField tfZarobki;

    public void init() {
        _pracownik = FormularzPierwszy.wybranyPracownik();
        lbPracownik.setText("Pracownik " + _pracownik.getImie() + " " + _pracownik.getNazwisko());

        tfZarobki.setText(String.valueOf(_pracownik.getWynagrodzenie()));
        cbStatus.setValue(_pracownik.getStanPracownika());

        cbStatus.getItems().setAll(EmployeeCondition.values());
    }

    @FXML
    void btnAnulujClicked(ActionEvent event) {
        Stage stage = (Stage) btnDodaj.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDodajClicked(ActionEvent event) {

        try {
            EmployeeCondition status = cbStatus.getValue();
            if(status == null)
                throw new Exception();


            double zarobki = Double.valueOf(tfZarobki.getText());
            if(zarobki < 0)
                throw new Exception();

            FormularzPierwszy.zaktualizujPracownika(status, zarobki);
            Stage stage = (Stage) btnDodaj.getScene().getWindow();
            stage.close();
        }catch (Exception ex)
        {
            showError("Źle wprowadzone dane!");
        }
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
