package main.java.hr.java.covidportal.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DodajNoviSimptomNit;
import main.java.hr.java.covidportal.niti.DodajNovuZupanijuNit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DodavanjeNovogSimptomaController implements Initializable {
    @FXML
    private TextField unosNaziva;
    @FXML
    private  TextField unosVrijednosti;


    @FXML
    public void dodajSimptom() throws SQLException, IOException, InterruptedException {
        List<Simptom> simptomi;
        simptomi = BazaPodataka.dohvatiSimptome();
        if(unosNaziva.getText() != "" && unosNaziva.getText() != ""){
            Simptom priv = new Simptom(unosNaziva.getText(),null,unosVrijednosti.getText());
            DodajNoviSimptomNit dodajNoviSimptomNit = new DodajNoviSimptomNit(priv);
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(dodajNoviSimptomNit);

            executorService.shutdown();
            //BazaPodataka.dodajNoviSimptom(priv);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dodavanje novog simptoma");
            alert.setHeaderText("Uspješno dodano");
            alert.setContentText("Uspješno ste dodali novi simptom!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje novog simptoma");
            alert.setHeaderText("Nespješno dodano");
            alert.setContentText("Unesite sve tražene podatke");
            alert.showAndWait();
        }



    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
