package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DohvatiSimptomeNIt implements Callable<List<Simptom>> {


    @Override
    public synchronized List<Simptom> call() throws Exception {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        List<Simptom> simptomi = BazaPodataka.dohvatiSimptome();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();


        return simptomi;
    }
}
