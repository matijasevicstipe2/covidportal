package main.java.hr.java.covidportal.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.niti.DodajNovuBolestNit;

import javax.management.BadAttributeValueExpException;
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

public class DodavanjeNoveBolestiController implements Initializable {

    @FXML
    private ListView<String> simptomiString;
    @FXML
    private TextField nazivBolesti;

   @FXML
    public void dodajBolest() throws SQLException, IOException {

       ObservableList<String> odabirSimptomaList;
       odabirSimptomaList = simptomiString.getSelectionModel().getSelectedItems();
       Boolean jeliVirus = false;
       if(nazivBolesti.getText() != "" && !odabirSimptomaList.isEmpty()){
           DodajNovuBolestNit dodajNovuBolestNit = new DodajNovuBolestNit(nazivBolesti,odabirSimptomaList,jeliVirus);
           ExecutorService executorService = Executors.newCachedThreadPool();
           executorService.execute(dodajNovuBolestNit);

           executorService.shutdown();
           //BazaPodataka.dodajNovuBolest(nazivBolesti, odabirSimptomaList,jeliVirus);

           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Dodavanje nove bolesti");
           alert.setHeaderText("Uspješno dodano");
           alert.setContentText("Uspješno ste dodali novu bolest!");
           alert.showAndWait();
       }else{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Dodavanje nove bolesti");
           alert.setHeaderText("Nespješno dodano");
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

