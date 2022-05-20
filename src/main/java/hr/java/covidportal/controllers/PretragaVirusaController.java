package main.java.hr.java.covidportal.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Virus;
import main.java.hr.java.covidportal.niti.DohvatiBolestiNit;
import main.java.hr.java.covidportal.niti.DohvatiSimptomeNIt;

import javax.management.BadAttributeValueExpException;
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

public class PretragaVirusaController  implements Initializable {


    @FXML
    private TextField nazivVirusaTextField;
    @FXML
    private TableView<Bolest> tablicaVirusa;
    @FXML
    private TableColumn<Virus,String> nazivVirusaColumn;
    @FXML
    private TableColumn<Virus,String> simptomiVirusaColumn;

    @FXML
    public void pretraziViruse() throws SQLException, IOException, ExecutionException, InterruptedException {
        List<Bolest> virusi = new ArrayList<>();
        List<Bolest> pomVirusi = new ArrayList<>();
        final ObservableList<Bolest> listaVirusa;

        Boolean jeliVirus = true;
        //virusi = BazaPodataka.dohvatiBolesti(jeliVirus);
        DohvatiBolestiNit dohvatiViruseNit = new DohvatiBolestiNit(jeliVirus);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Bolest>> future = executorService.submit(dohvatiViruseNit);
        virusi = future.get();
        executorService.shutdown();

        for(Bolest v : virusi){
            if(v.getNaziv().toLowerCase().contains(nazivVirusaTextField.getText().toLowerCase())){
                pomVirusi.add(v);
            }
        }
        listaVirusa = FXCollections.observableArrayList(pomVirusi);
        tablicaVirusa.setItems(listaVirusa);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazivVirusaColumn.setCellValueFactory(new PropertyValueFactory<Virus,String>("naziv"));
        simptomiVirusaColumn.setCellValueFactory(new PropertyValueFactory<Virus,String>("stringSimptomi"));
    }

}
