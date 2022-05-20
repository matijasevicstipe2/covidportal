package main.java.hr.java.covidportal.niti;

import javafx.application.Platform;
import main.java.hr.java.covidportal.database.BazaPodataka;
import main.java.hr.java.covidportal.model.Zupanija;
import main.java.hr.java.covidportal.sort.CovidSorter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static main.java.hr.java.covidportal.database.BazaPodataka.aktivnaVezaSBazomPodataka;

public class NajviseZarazenihNit implements Runnable{
    List<Zupanija> zupanije;


    public NajviseZarazenihNit(List<Zupanija> zupanije) {
        this.zupanije = zupanije;
    }

    public synchronized Zupanija nazivTrazeneZupanije() throws IOException, SQLException, InterruptedException {
        if(aktivnaVezaSBazomPodataka == true){
            wait();
        }
        aktivnaVezaSBazomPodataka = true;
        List<Zupanija> zupanijeZ = BazaPodataka.dohvatiZupanije();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();
        double trazeniPostotak = zupanijeZ.stream()
                .mapToDouble(z ->z.getPostotak())
                .max()
                .getAsDouble();

        Zupanija trazenaZupanija = zupanijeZ.stream()
                .filter(zupanija -> zupanija.getPostotak() == trazeniPostotak)
                .findFirst().get();

        return trazenaZupanija;
    }

    @Override
    public void run() {
        while (true) {
            Zupanija z = null;
            try {
                z = nazivTrazeneZupanije();
            } catch (IOException | SQLException | InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(z.getNaziv() +
                    " ima najviše zaraženih sa postotkom " + z.getPostotak());
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }
    }
}
