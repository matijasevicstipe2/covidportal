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
import main.java.hr.java.covidportal.main.Main;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Virus;
import main.java.hr.java.covidportal.niti.DohvatiBolestiNit;
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

public class PretragaBolestiController implements Initializable {

    @FXML
    private TextField nazivTextField;
    @FXML
    private TableView<Bolest> tablicaBolesti;
    @FXML
    private TableColumn<Bolest,String> nazivBolestiColumn;
    @FXML
    private TableColumn<Bolest,String> simptomiBolestiColumn;

    @FXML
    public void pretraziBolesti() throws SQLException, IOException, ExecutionException, InterruptedException {
        List<Bolest> bolesti = new ArrayList<>();
        List<Bolest> pomBolesti = new ArrayList<>();
        final ObservableList<Bolest> listaBolesti;

        Boolean jeliVirus = false;
        //bolesti = BazaPodataka.dohvatiBolesti(jeliVirus);

        DohvatiBolestiNit dohvatiBolestiNit = new DohvatiBolestiNit(jeliVirus);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Bolest>> future = executorService.submit(dohvatiBolestiNit);
        bolesti = future.get();
        executorService.shutdown();

        for(Bolest b : bolesti){
            if(b.getNaziv().toLowerCase().contains(nazivTextField.getText().toLowerCase())){
                pomBolesti.add(b);
            }
        }
        listaBolesti = FXCollections.observableArrayList(pomBolesti);
        tablicaBolesti.setItems(listaBolesti);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazivBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest,String>("naziv"));
        simptomiBolestiColumn.setCellValueFactory(new PropertyValueFactory<Bolest,String>("stringSimptomi"));
    }

}
