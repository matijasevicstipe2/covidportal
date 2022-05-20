package main.java.hr.java.covidportal.model;

import java.io.Serializable;

/**
 * Predstavlja entitet za nazive kod ostalih klasa
 */

public abstract class ImenovaniEntitet implements Serializable {

    protected String naziv;
    protected Long id;

    /**
     * Inicijalizira podatak o nazivu
     * @param naziv podatak o nazivu
     */

    public ImenovaniEntitet(String naziv,Long id) {
        this.naziv = naziv;
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
