package main.java.hr.java.covidportal.niti;

import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Zupanija;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DodajNovuZupanijuNit implements Runnable {
    TextField t1;
    TextField t2;
    TextField t3;

    public DodajNovuZupanijuNit(TextField t1, TextField t2, TextField t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    @Override
    public synchronized void run() {
        try {
            if(aktivnaVezaSBazomPodataka == true){
                wait();
            }
            aktivnaVezaSBazomPodataka = true;
            BazaPodataka.dodajNovuZupaniju(t1,t2,t3);
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
