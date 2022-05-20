package main.java.hr.java.covidportal.niti;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.database.BazaPodataka;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DodajNovuOsobuNit implements Runnable{
    TextField t1;
    TextField t2;
    DatePicker datePicker;
    ObservableList<String> obsZupanija;
    ObservableList<String> obsBolest;
    ObservableList<String> obsKontakt;

    public DodajNovuOsobuNit(TextField t1, TextField t2,
                             DatePicker datePicker, ObservableList<String> obsZupanija,
                             ObservableList<String> obsBolest, ObservableList<String> obsKontakt) {
        this.t1 = t1;
        this.t2 = t2;
        this.datePicker = datePicker;
        this.obsZupanija = obsZupanija;
        this.obsBolest = obsBolest;
        this.obsKontakt = obsKontakt;
    }

    @Override
    public synchronized void run() {
        try {
            if(aktivnaVezaSBazomPodataka == true){
                wait();
            }
            aktivnaVezaSBazomPodataka = true;
            BazaPodataka.dodajNovuOsobu(t1,t2,datePicker,obsZupanija,obsBolest,obsKontakt);
            aktivnaVezaSBazomPodataka = false;
            notifyAll();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
