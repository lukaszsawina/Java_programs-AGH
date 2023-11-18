package com.example.menagerpracownikow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.text.Normalizer;

public class NewgroupController {
    private IDataCommunicator FormularzPierwszy;

    @FXML
    private Button btnAnuluj;

    @FXML
    private Button btnDodaj;

    @FXML
    private TextField tfIlosc;

    @FXML
    private TextField tfNazwaGrupy;

    @FXML
    void btnAnulujClicked(ActionEvent event) {
        Stage stage = (Stage) btnAnuluj.getScene().getWindow();

        stage.close();
    }

    @FXML
    void btnDodajClicked(ActionEvent event) {
        String nazwa = tfNazwaGrupy.getText();

        try
        {
            int pojemnosc = Integer.valueOf(tfIlosc.getText());

            if(!FormularzPierwszy.czyGrupaIstnieje(nazwa))
            {
                FormularzPierwszy.przekazDaneGrupy(nazwa, pojemnosc);

                Stage stage = (Stage) btnDodaj.getScene().getWindow();
                stage.close();
            }
            else
            {
                showError("Grupa o takiej nazwie już istnieje!");
            }
        }catch (Exception ex)
        {
            showError("Pojemność nie jest liczbą!");
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
