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
import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DohvatiSimptomeNIt;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

public class PretragaSimptomaController implements Initializable {

    @FXML
    private TextField nazivSimp;
    @FXML
    private TextField vrijednostSimp;
    @FXML
    private TableView<Simptom> tablicaSimptoma;
    @FXML
    private TableColumn<Simptom,String> nazivColumn;
    @FXML
    private TableColumn<Simptom,String> vrijednostColumn;

    @FXML
    public void pretraziSimptome() throws ExecutionException, InterruptedException {
        List<Simptom> simptomi = new ArrayList<>();
        List<Simptom> pomSimptomi = new ArrayList<>();
        final ObservableList<Simptom> listaSimptoma;

        DohvatiSimptomeNIt dohvatiSimptomeNIt = new DohvatiSimptomeNIt();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Simptom>> future = executorService.submit(dohvatiSimptomeNIt);
        simptomi = future.get();
        executorService.shutdown();

        for(Simptom s : simptomi){
            if(s.getNaziv().toLowerCase().contains(nazivSimp.getText().toLowerCase()) &&
            s.getVrijednost().toLowerCase().contains(vrijednostSimp.getText().toLowerCase())){
                pomSimptomi.add(s);
            }
        }
        listaSimptoma = FXCollections.observableArrayList(pomSimptomi);
        tablicaSimptoma.setItems(listaSimptoma);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazivColumn.setCellValueFactory(new PropertyValueFactory<Simptom,String>("naziv"));
        vrijednostColumn.setCellValueFactory(new PropertyValueFactory<Simptom,String>("vrijednost"));
    }
}
