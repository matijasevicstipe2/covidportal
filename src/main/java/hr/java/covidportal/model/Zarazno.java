package main.java.hr.java.covidportal.model;

/**
 * Predstavlja metode za prijenos zarazre
 */

public interface Zarazno {
    /**
     * neinicijalizirana metoda koja sluzi za override u nekim specificinim slucajevima
     * @param osoba podatak o osobi koja se zarazuje
     */
    public void prelazakZarazeNaOsobu(Osoba osoba);


}
