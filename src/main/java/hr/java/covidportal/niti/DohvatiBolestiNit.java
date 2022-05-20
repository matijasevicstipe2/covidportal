package main.java.hr.java.covidportal.niti;

import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Zupanija;

import java.util.List;
import java.util.concurrent.Callable;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class DohvatiBolestiNit implements Callable<List<Bolest>> {
    Boolean virus;

    public DohvatiBolestiNit(Boolean virus) {
        this.virus = virus;
    }

    @Override
    public synchronized List<Bolest> call() throws Exception {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        List<Bolest> bolesti = BazaPodataka.dohvatiBolesti(virus);
        aktivnaVezaSBazomPodataka = false;
        notifyAll();

        return bolesti;
    }
}
