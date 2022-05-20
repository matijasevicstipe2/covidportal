package main.java.hr.java.covidportal.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import main.java.hr.java.covidportal.main.Main;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.niti.DohvatiBolestiNit;
import main.java.hr.java.covidportal.niti.SimptomiCountNit;
import main.java.hr.java.covidportal.niti.ZadnjiUneseniSimptomNit;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PocetniEkranController implements Initializable {

    //private Menu zupanijeMenu;
    @FXML
    private BorderPane borderPane;

    @FXML
    private Label ukupanBrojSimptoma;
    @FXML
    private Label vrijemeDohvacanja;
    @FXML
    private Label imeSimptoma;
    @FXML
    private Label vrijednostSimptoma;
    @FXML
    private Label vrijeme;

    @FXML
    public void prikaziEkranZaPretraguZupanija() throws IOException {
        Parent pretragaZupanijaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaZupanija.fxml"));
        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaZupanijaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguSimptoma() throws IOException {
        Parent pretragaSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaSimptoma.fxml"));
        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame, 600, 400);
        //pretragaSimptomaScene.getStylesheets()
        Main.getMainStage().setScene(pretragaSimptomaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguBolesti() throws IOException {
        Parent pretragaBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaBolesti.fxml"));
        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame, 600, 400);
        Main.getMainStage().setScene(pretragaBolestiScene);
    }
    @FXML
    public void prikaziEkranZaPretraguVirusa() throws IOException {
        Parent pretragaVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaVirusi.fxml"));
        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaVirusaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguOsoba() throws IOException {
        Parent pretragaOsobaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("pretragaOsoba.fxml"));
        Scene pretragaOsobaScene = new Scene(pretragaOsobaFrame, 600, 400);
        Main.getMainStage().setScene(pretragaOsobaScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeZupanije() throws IOException {
        Parent dodavanjeZupanijeFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveZupanije.fxml"));
        Scene dodavanjeZupanijeScene = new Scene(dodavanjeZupanijeFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeZupanijeScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeSimptoma() throws IOException {
        Parent dodavanjeSimptomaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNovogSimptoma.fxml"));
        Scene dodavanjeSimptomaScene = new Scene(dodavanjeSimptomaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeSimptomaScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeBolesti() throws IOException {
        Parent dodavanjeBolestiFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveBolesti.fxml"));
        Scene dodavanjeBolestiScene = new Scene(dodavanjeBolestiFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeBolestiScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeVirusa() throws IOException {
        Parent dodavanjeVirusaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNovogVirusa.fxml"));
        Scene dodavanjeVirusaScene = new Scene(dodavanjeVirusaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeVirusaScene);
    }
    @FXML
    public void prikaziEkranZaDodavanjeOsoba() throws IOException {
        Parent dodavanjeOsobaFrame =
                FXMLLoader.load(getClass().getClassLoader().getResource("dodavanjeNoveOsobe.fxml"));
        Scene dodavanjeOsobaScene = new Scene(dodavanjeOsobaFrame, 600, 400);
        Main.getMainStage().setScene(dodavanjeOsobaScene);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //prvi zadatak labos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    try {
                        SimptomiCountNit simptomiCountNit = new SimptomiCountNit();
                        ExecutorService executorService = Executors.newCachedThreadPool();
                        Future<Integer> future = executorService.submit(simptomiCountNit);
                        ukupanBrojSimptoma.setText("Ukupan broj simptoma je: " + future.get());
                        executorService.shutdown();
                        LocalTime time = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        vrijemeDohvacanja.setText(time.format(formatter));
                    } catch (InterruptedException | ExecutionException ioException) {
                        ioException.printStackTrace();
                    }

                }),new KeyFrame(Duration.seconds(2))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //drugi zadatak labos
        Timeline timelineSecond = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    try {

                        ZadnjiUneseniSimptomNit zadnjiUneseniSimptomNit = new ZadnjiUneseniSimptomNit();
                        ExecutorService executorServiceDrugi = Executors.newCachedThreadPool();
                        Future<Simptom> futureTwo = executorServiceDrugi.submit(zadnjiUneseniSimptomNit);
                        imeSimptoma.setText("Ime zadnjeg simptoma: " + futureTwo.get().getNaziv());
                        vrijednostSimptoma.setText("[Vrijednost zadnjeg simptoma je: "
                                + futureTwo.get().getVrijednost());
                        executorServiceDrugi.shutdown();
                        LocalTime time = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        vrijeme.setText(time.format(formatter) + "]   ");
                    } catch (InterruptedException | ExecutionException ioException) {
                        ioException.printStackTrace();
                    }

                }),new KeyFrame(Duration.seconds(5))
        );
        timelineSecond.setCycleCount(Animation.INDEFINITE);
        timelineSecond.play();


    }

}
