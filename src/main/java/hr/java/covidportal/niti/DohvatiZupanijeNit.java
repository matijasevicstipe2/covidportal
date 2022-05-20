package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Zupanija;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DohvatiZupanijeNit implements Callable<List<Zupanija>> {

    @Override
    public synchronized List<Zupanija> call() throws Exception {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        List<Zupanija> zupanije = BazaPodataka.dohvatiZupanije();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();

        return zupanije;
    }
}
