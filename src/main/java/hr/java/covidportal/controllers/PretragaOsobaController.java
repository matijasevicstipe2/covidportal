package main.java.hr.java.covidportal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.*;
import main.java.hr.java.covidportal.niti.DohvatiOsobeNit;
import main.java.hr.java.covidportal.niti.DohvatiSimptomeNIt;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PretragaOsobaController  implements Initializable {

    @FXML
    private TextField imeTextField;
    @FXML
    private TextField prezimeTextField;
    @FXML
    private TableView<Osoba> tablicaOsoba;
    @FXML
    private TableColumn<Osoba,String> imeColumn;
    @FXML
    private TableColumn<Osoba,String> prezimeColumn;
    @FXML
    private TableColumn<Osoba,String> godineColumn;
    @FXML
    private TableColumn<Osoba,String> zupanijaColumn;
    @FXML
    private TableColumn<Osoba,String> zarazenColumn;
    @FXML
    private TableColumn<Osoba,String> kontaktiColumn;

    @FXML
    public void pretraziOsobe() throws SQLException, IOException, ExecutionException, InterruptedException {
        List<Osoba> osobe = new ArrayList<>();
        List<Osoba> pomOsobe= new ArrayList<>();
        final ObservableList<Osoba> listaOsoba;
        DohvatiOsobeNit dohvatiOsobeNit = new DohvatiOsobeNit();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Osoba>> future = executorService.submit(dohvatiOsobeNit);
        osobe = future.get();
        executorService.shutdown();

        for(Osoba o : osobe){
            if(o.getIme().toLowerCase().contains(imeTextField.getText().toLowerCase()) &&
            o.getPrezime().toLowerCase().contains(prezimeTextField.getText().toLowerCase())){
                pomOsobe.add(o);
            }
        }
        listaOsoba = FXCollections.observableArrayList(pomOsobe);
        tablicaOsoba.setItems(listaOsoba);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        godineColumn.setCellValueFactory(new PropertyValueFactory<Osoba,String>("godineString"));
        zupanijaColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zupanijaString"));
        zarazenColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("zarazenString"));
        kontaktiColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("kontaktiString"));
    }

}
