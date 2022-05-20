package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Predstavlja entitet zupanije definarane nazivom i brojem i stanovnika.
 */

public  class Zupanija extends ImenovaniEntitet implements Serializable {

    private Integer brojStanovnika;
    private Integer brojZarazenih;

    /**
     * Inicijalizira podatak o broju stanovnika,a naziv salje superklasi <code>ImenovaniEntitet</code>
     * @param naziv podatak o nazivu
     * @param brojStanovnika podatak o brojuStanovnika
     */

    public Zupanija(String naziv,Long id, Integer brojStanovnika,Integer brojZarazenih) {
        super(naziv,id);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;

    }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }
    public double getPostotak(){
        double p;
        p = ((double)this.brojZarazenih / (double)this.brojStanovnika)*100;
        return p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zupanija)) return false;
        Zupanija zupanija = (Zupanija) o;
        return Objects.equals(brojStanovnika, zupanija.brojStanovnika) && Objects.equals(brojZarazenih, zupanija.brojZarazenih);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brojStanovnika,brojZarazenih);
    }

    @Override
    public String toString() {
        return "Zupanija{" +
                "brojStanovnika=" + brojStanovnika +
                ", brojZarazenih=" + brojZarazenih +
                ", naziv='" + naziv + '\'' +
                ", id=" + id +
                '}';
    }
}