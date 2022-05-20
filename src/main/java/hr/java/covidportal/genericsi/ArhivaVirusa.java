package main.java.hr.java.covidportal.genericsi;

import main.java.hr.java.covidportal.model.Virus;

import java.util.ArrayList;
import java.util.List;

public class ArhivaVirusa <T extends Virus> {

    private List<T> virusi;

    //prazni konstruktor
    public ArhivaVirusa() {
        virusi = new ArrayList<>();
    }
    //konstruktor kojj prima listu virusa
    public ArhivaVirusa(List<T> arhiva) {
        this.virusi = arhiva;
    }
    //dodaj novi virus
    public void add(T t){
        this.virusi.add(t);
    }
    //dodaj cijelu listu virusa
    public void setVirusi(List<T> virusi) {
        this.virusi = virusi;
    }
    //dohvati odredeni virus
    public T get(Integer i){
        return virusi.get(i);

    }
    //dohvati cijelu listu virusa
    public List<T> getVirusi() {
        return virusi;
    }

    @Override
    public String toString() {
        return "ArhivaVirusa: " +
                "virusi= " + virusi +
                '}';
    }
}
