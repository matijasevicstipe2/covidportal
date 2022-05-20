package main.java.hr.java.covidportal.model;

import java.util.List;

/**
 * Predstavlja entitet virusa definiranog sa nazivom i simptomima
 * nasljeduje klasu Bolest i implementira sucelje Zarazno
 */
public class Virus extends Bolest implements Zarazno {

    /**
     * Salje parametre superklasi <code>Bolest</code>
     * @param naziv podatak o nazivu
     * @param simptomi podatak o simptomima
     */

    public Virus(String naziv,Long id, List<Simptom> simptomi){
        super(naziv,id,simptomi,2);


    }

    /**
     * Overridea metodu iz sucelja Zarazno koja parametru osoba postavlja zarazu tim virusom.
     * @param osoba podatak o osobi koja se zarazuje tim virusom
     */
    @Override
    public void prelazakZarazeNaOsobu(Osoba osoba) {
        osoba.setZarazenBolescu(this);
    }


    @Override
    public String toString() {
        return "Virus{" +
                "naziv='" + naziv + '\'' +
                '}';
    }
}
