package main.java.hr.java.covidportal.niti;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.database.BazaPodataka;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;


public class DodajNovuBolestNit implements Runnable{
    TextField t1;
    ObservableList<String> observableList;
    Boolean jeliVirus;

    public DodajNovuBolestNit(TextField t1, ObservableList<String> observableList, Boolean jeliVirus) {
        this.t1 = t1;
        this.observableList = observableList;
        this.jeliVirus = jeliVirus;
    }

    @Override
    public synchronized void run() {
        try {
            if(aktivnaVezaSBazomPodataka == true){
                wait();
            }
            aktivnaVezaSBazomPodataka = true;
            BazaPodataka.dodajNovuBolest(t1,observableList,jeliVirus);
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
