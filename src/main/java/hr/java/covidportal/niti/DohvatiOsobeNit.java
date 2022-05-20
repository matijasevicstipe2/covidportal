package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Zupanija;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DohvatiOsobeNit implements Callable<List<Osoba>> {

    @Override
    public synchronized List<Osoba> call() throws Exception {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        List<Osoba> osobe = BazaPodataka.dohvatiOsobe();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();

        return osobe;
    }
}
