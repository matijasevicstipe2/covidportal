package main.java.hr.java.covidportal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
//drugi zadatak labos
public class ZupanijaDetaljiController implements Initializable {
    @FXML
    private TableView<Osoba> tablicaOsoba;
    @FXML
    private TableColumn<Osoba,String> imeColumn;
    @FXML
    private TableColumn<Osoba,String> prezimeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
    }
    public void prikaziZupaniju(Zupanija zupanija) throws IOException, SQLException, InterruptedException {

        List<Osoba> osobe;
        final ObservableList<Osoba> listaOsoba;
        osobe = BazaPodataka.dohvatiOsobeIzZupanije(zupanija.getId());


        listaOsoba = FXCollections.observableArrayList(osobe);
        tablicaOsoba.setItems(listaOsoba);
    }
}
