package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Predstavlja entitet osobe koja je definirana imenom,prezimenom,godinama,zupanijom,zarazenom bolesti i kontaktiranim osobama
 */

public class Osoba implements Serializable {
    private String ime;
    private String prezime;
    private Long godine;
    private Long id;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontakti = new ArrayList<>();

    /**
     * Koristimo je kada konstruktor prima puno parametara i/ili kada ne treba inicijalizirati sve atribute klase
     */


    public static class Builder {

        private String ime;
        private String prezime;
        private Long godine;
        private Long id;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontakti = new ArrayList<>();
        private static int check = 0;

        /**
         * Inicijalizira samo bitnije atribute klase Osoba
         * @param ime podatak o imenu
         * @param prezime podatak o prezimenu
         * @param godine podatak o godinama
         */

        public Builder(String ime, String prezime, Long godine, Long id){
            this.ime = ime;
            this.prezime = prezime;
            this.godine = godine;
            this.id = id;
        }

        public Builder zup(Zupanija zup){
            this.zupanija = zup;
            return this;
        }
        public Builder zaraBol(Bolest zarazenBolescu){
            this.zarazenBolescu = zarazenBolescu;
            return this;

        }
        public Builder kon(List<Osoba> kontakti){
            this.kontakti = kontakti;
            return this;
        }
        public Osoba build(){
            Osoba osoba = new Osoba();
            check = 0;
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.godine = this.godine;
            osoba.id = this.id;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontakti = this.kontakti;
            if(this.zarazenBolescu instanceof Virus virus){

                virus.setNaziv(this.zarazenBolescu.getNaziv());
                int i = 0;
                if( this.kontakti != null) {

                    while (i < this.kontakti.size()) {
                        System.out.println(i);
                        virus.prelazakZarazeNaOsobu(kontakti.get(i));
                        i++;
                    }
                }

            }
            return osoba;
        }

    }
    private Osoba(){

    }
    public String getGodineString(){
        String godineString;
        godineString = "" + godine;
        return godineString;
    }
    public String getKontaktiString(){
        if(this.kontakti != null){
            String kontaktiString = "";
            for(int i = 0;i < kontakti.size();i++){
                kontaktiString = kontaktiString + kontakti.get(i).getIme() + " " + kontakti.get(i).getPrezime();
                if(i != (kontakti.size() - 1) ){
                    kontaktiString = kontaktiString + ",";
                }
            }
            return kontaktiString;
        }else {
            String kontaktiString = "";
            return kontaktiString;
        }
    }
    public String getZupanijaString(){
        String zupanijaString;
        zupanijaString = zupanija.getNaziv();

        return zupanijaString;
    }
    public String getZarazenString(){

        String zarazenString;
        zarazenString = this.zarazenBolescu.getNaziv();
        return zarazenString;
    }
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Long getGodine() {
        return godine;
    }

    public void setGodine(Long godine) {
        this.godine = godine;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }


    public List<Osoba> getKontakti() {
        return kontakti;
    }

    public void setKontakti(List<Osoba> kontakti) {
        this.kontakti = kontakti;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Osoba)) return false;
        Osoba osoba = (Osoba) o;
        return Objects.equals(ime, osoba.ime) &&
                Objects.equals(prezime, osoba.prezime) &&
                Objects.equals(godine, osoba.godine) &&
                Objects.equals(zupanija, osoba.zupanija) &&
                Objects.equals(zarazenBolescu, osoba.zarazenBolescu) &&
                Objects.equals(kontakti, osoba.kontakti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ime, prezime, godine, zupanija, zarazenBolescu, kontakti);
    }
}