package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Simptom;

import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class ZadnjiUneseniSimptomNit implements Callable<Simptom> {

    @Override
    public  synchronized Simptom call() throws Exception {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        Simptom simptom = BazaPodataka.zadnjiUneseniSimptom();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();

        return simptom;

    }
}
