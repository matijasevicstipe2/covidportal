package main.java.hr.java.covidportal.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.main.Glavna;
import main.java.hr.java.covidportal.main.Main;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DohvatiSimptomeNIt;
import main.java.hr.java.covidportal.niti.DohvatiZupanijeNit;
import main.java.hr.java.covidportal.niti.NajviseZarazenihNit;

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

public class PretragaZupanijaController implements Initializable {

    @FXML
    private TextField stringZaPretragu;
    @FXML
    private TableView<Zupanija> table;
    @FXML
    private TableColumn<Zupanija,String> naziv;
    @FXML
    private TableColumn<Zupanija,String> brS;
    @FXML
    private TableColumn<Zupanija,String> brZ;
    @FXML
    private TableColumn<Zupanija,String> id;

    private static boolean inicijalizirano = true;

    @FXML
    public void pretraziZupanije() throws SQLException, IOException, ExecutionException, InterruptedException {

        List<Zupanija> zupanije;
        List<Zupanija> pomZupanije = new ArrayList<>();
        final ObservableList<Zupanija> listaZupanija;


        DohvatiZupanijeNit dohvatiZupanijeNit = new DohvatiZupanijeNit();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<List<Zupanija>> future = executorService.submit(dohvatiZupanijeNit);
        zupanije = future.get();

        executorService.shutdown();
        for(Zupanija z :zupanije){
            if(z.getNaziv().toLowerCase().contains(stringZaPretragu.getText().toLowerCase())){
                pomZupanije.add(z);
            }
        }
        listaZupanija = FXCollections.observableArrayList(pomZupanije);
        table.setItems(listaZupanija);


    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        naziv.setCellValueFactory(new PropertyValueFactory<Zupanija,String>("naziv"));
        brS.setCellValueFactory( new PropertyValueFactory<Zupanija,String>("brojStanovnika"));
        brZ.setCellValueFactory(new PropertyValueFactory<Zupanija,String>("brojZarazenih"));
        id.setCellValueFactory(new PropertyValueFactory<Zupanija,String>("id"));
        List<Zupanija> pomocneZupanije = new ArrayList<>();
        try {
            pomocneZupanije  = BazaPodataka.dohvatiZupanije();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        if(inicijalizirano){

            NajviseZarazenihNit najviseZarazenihNit = new NajviseZarazenihNit(pomocneZupanije);
            ExecutorService executorService = Executors.newCachedThreadPool();
            executorService.execute(najviseZarazenihNit);

            executorService.shutdown();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, e -> {
                        Zupanija zupanija = null;
                        try {
                            zupanija = najviseZarazenihNit.nazivTrazeneZupanije();
                        } catch (IOException | InterruptedException | SQLException ioException) {
                            ioException.printStackTrace();
                        }
                        Main.getMainStage().setTitle(zupanija.getNaziv());
                    }),new KeyFrame(Duration.seconds(1))
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
           inicijalizirano = false;
        }


        //IMP
        

    }
    public void clicked(Zupanija zupanija){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("zupanijaDetalji.fxml"));
            Stage stg = new Stage();
            stg.setTitle("Prikaz zupanija");
            stg.setScene(new Scene(loader.load(),600,400));
            stg.show();
            ZupanijaDetaljiController zControl = loader.getController();
            zControl.prikaziZupaniju(zupanija);


        }catch (IOException | SQLException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
