package main.java.hr.java.covidportal.genericsi;

import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Virus;

import java.util.ArrayList;
import java.util.List;

public class KlinikaZaInfektivneBolesti <T extends Virus,S extends Osoba> {

    private List<T> listaVirusa;
    private List<S> listaOsobaSaVirusom;

    public KlinikaZaInfektivneBolesti() {
        listaVirusa = new ArrayList<>();
    }


    public KlinikaZaInfektivneBolesti(List<T> listaVirusa, List<S> listaOsobaSaVirusom) {
        this.listaVirusa = listaVirusa;
        this.listaOsobaSaVirusom = listaOsobaSaVirusom;
    }

    public List<T> getListaVirusa() {
        return listaVirusa;
    }
    public void dodajVirus(T t){
        this.listaVirusa.add(t);
    }

    public List<S> getListaOsobaSaVirusom() {
        return listaOsobaSaVirusom;
    }
    public void dodajOsobu(S s){
        this.listaOsobaSaVirusom.add(s);
    }

    public void setListaVirusa(List<T> listaVirusa) {
        this.listaVirusa = listaVirusa;
    }

    public void setListaOsobaSaVirusom(List<S> listaOsobaSaVirusom) {
        this.listaOsobaSaVirusom = listaOsobaSaVirusom;
    }
}
