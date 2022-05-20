package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Simptom;

import java.io.IOException;
import java.sql.SQLException;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DodajNoviSimptomNit implements Runnable {
    private Simptom simptom;

    public DodajNoviSimptomNit(Simptom simptom) {
        this.simptom = simptom;
    }

    @Override
    public synchronized void run() {
        try {
            if(aktivnaVezaSBazomPodataka == true){
                wait();
            }
            aktivnaVezaSBazomPodataka = true;
            BazaPodataka.dodajNoviSimptom(simptom);
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
