package main.java.hr.java.covidportal.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Virus;
import main.java.hr.java.covidportal.niti.DodajNovuBolestNit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DodavanjeNovogVirusaController implements Initializable {

    @FXML
    private ListView<String> simptomiString;
    @FXML
    private TextField nazivVirusa;

    @FXML
    public void dodajVirus() throws SQLException, IOException {

        Boolean jeliVirus = true;
        ObservableList<String> odabirSimptomaList;
        odabirSimptomaList = simptomiString.getSelectionModel().getSelectedItems();

        if(nazivVirusa.getText() != "" && !odabirSimptomaList.isEmpty()){
            DodajNovuBolestNit dodajNovuBolestNit = new DodajNovuBolestNit(nazivVirusa,odabirSimptomaList,jeliVirus);
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(dodajNovuBolestNit);

            executorService.shutdown();
            //BazaPodataka.dodajNovuBolest(nazivVirusa,odabirSimptomaList,jeliVirus);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dodavanje novog virusa");
            alert.setHeaderText("Uspješno dodano");
            alert.setContentText("Uspješno ste dodali novi virus!");
            alert.showAndWait();
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje novog virusa");
            alert.setHeaderText("Nepješno dodano");
            alert.setContentText("Unesite sve tražene podatke");
            alert.showAndWait();
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Simptom> simptomList = new ArrayList<>();
        try {
            simptomList = BazaPodataka.dohvatiSimptome();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        List<String> imenaSimptoma;
        imenaSimptoma = simptomList.stream().map(simptom -> simptom.getNaziv()).collect(Collectors.toList());

        simptomiString.getItems().addAll(imenaSimptoma);
        simptomiString.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
}



