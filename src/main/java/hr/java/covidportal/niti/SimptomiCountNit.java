package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Bolest;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

//prvi zadatak labos
public class SimptomiCountNit implements Callable<Integer> {

    @Override
    public synchronized Integer call() throws Exception {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        int trazeniBroj;
        trazeniBroj = BazaPodataka.dohvatiCountSimptoma();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();

        return trazeniBroj;
    }
}
