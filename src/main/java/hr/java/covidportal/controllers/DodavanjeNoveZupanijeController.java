package main.java.hr.java.covidportal.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DodajNovuZupanijuNit;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.LinkOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DodavanjeNoveZupanijeController implements Initializable {

    @FXML
    private TextField unosNaziva;
    @FXML
    private  TextField unosBrStan;
    @FXML
    private  TextField unosBrZaraz;

    @FXML
    public void dodajZupaniju() throws SQLException, IOException {
        if(unosNaziva.getText() != "" && unosBrStan.getText() != "" && unosBrZaraz.getText() != ""){

            DodajNovuZupanijuNit dodajNovuZupanijuNit = new DodajNovuZupanijuNit(unosNaziva,unosBrStan,unosBrZaraz);
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(dodajNovuZupanijuNit);

            executorService.shutdown();

            //BazaPodataka.dodajNovuZupaniju(unosNaziva,unosBrStan,unosBrZaraz);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dodavanje nove županije");
            alert.setHeaderText("Uspješno dodano");
            alert.setContentText("Uspješno ste dodali novu županiju!");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje nove županije");
            alert.setHeaderText("Nespješno dodano");
            alert.setContentText("Unesite sve tražene podatke");
            alert.showAndWait();
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
