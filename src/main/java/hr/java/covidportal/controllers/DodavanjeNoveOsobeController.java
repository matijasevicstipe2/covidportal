package main.java.hr.java.covidportal.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DodajNovuOsobuNit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DodavanjeNoveOsobeController implements Initializable {

    @FXML
    private TextField imeOsobe;
    @FXML
    private TextField prezimeOsobe;
    @FXML
    private DatePicker godineOsobe;
    @FXML
    private ListView<String> zupanijaOsobe;
    @FXML
    private ListView<String> bolestOsobe;
    @FXML
    private ListView<String> kontaktiOsobe;


    @FXML
    public void dodajOsobu() throws SQLException, IOException {
        ObservableList<String> odabirZupanijeList;
        odabirZupanijeList = zupanijaOsobe.getSelectionModel().getSelectedItems();

        ObservableList<String> odabirBolestiList;
        odabirBolestiList = bolestOsobe.getSelectionModel().getSelectedItems();

        ObservableList<String> odabirKontakataList;
        odabirKontakataList = kontaktiOsobe.getSelectionModel().getSelectedItems();

        if(imeOsobe.getText() !="" && prezimeOsobe.getText() !=""
                && godineOsobe.toString() !="" && !odabirZupanijeList.isEmpty()
                && !odabirBolestiList.isEmpty() && !odabirKontakataList.isEmpty()){
            DodajNovuOsobuNit dodajNovuOsobuNit = new DodajNovuOsobuNit(imeOsobe,prezimeOsobe,godineOsobe
                          ,odabirZupanijeList,odabirBolestiList,odabirKontakataList);

            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(dodajNovuOsobuNit);

            executorService.shutdown();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dodavanje nove osobe");
            alert.setHeaderText("Uspješno dodano");
            alert.setContentText("Uspješno ste dodali novu osobu!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje nove osobe");
            alert.setHeaderText("Nespješno dodano");
            alert.setContentText("Unesite sve tražene podatke");
            alert.showAndWait();
        }





    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Zupanija> zupanije = new ArrayList<>();
        try {
            zupanije = BazaPodataka.dohvatiZupanije();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        List<Bolest> bolesti = new ArrayList<>();
        try {
            bolesti =BazaPodataka.dohvatiSveBolesti();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        List<Osoba> osobe = new ArrayList<>();
        try {
            osobe = BazaPodataka.dohvatiOsobe();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

        List<String> imenaZupanija;
        imenaZupanija = zupanije.stream().map(zupanija -> zupanija.getNaziv()).collect(Collectors.toList());
        List<String> imenaBolesti;
        imenaBolesti = bolesti.stream().map(bolest -> bolest.getNaziv()).collect(Collectors.toList());
        List<String> imenaOsoba;
        imenaOsoba = osobe.stream().map(osoba -> osoba.getIme()).collect(Collectors.toList());

        zupanijaOsobe.getItems().addAll(imenaZupanija);
        zupanijaOsobe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bolestOsobe.getItems().addAll(imenaBolesti);
        bolestOsobe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        kontaktiOsobe.getItems().addAll(imenaOsoba);
        kontaktiOsobe.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }
}
