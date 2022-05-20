package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja entitet simptoma koji je definiran sa nazivom i vrijednoscu
 */
public class Simptom extends ImenovaniEntitet implements Serializable {
    private String vrijednost;

    /**
     * Inicjalizira podatak o vrijednosti,a naziv se salje suprklasi <code>ImenovaniEntitet</code>
     * @param naziv podatak o nazivu
     * @param vrijednost podatak o vrijednosti
     */

    public Simptom(String naziv,Long id,String vrijednost) {
        super(naziv,id);
        this.vrijednost = vrijednost;
    }

    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }

    /*@Override
    public int compareTo(Simptom o) {
        return this.naziv.compareTo(o.getNaziv());
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Simptom)) return false;
        Simptom simptom = (Simptom) o;
        return Objects.equals(vrijednost, simptom.vrijednost) && Objects.equals(naziv, simptom.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vrijednost);
    }

    @Override
    public String toString() {
        return "Simptom{" +
                "vrijednost='" + vrijednost + '\'' +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}