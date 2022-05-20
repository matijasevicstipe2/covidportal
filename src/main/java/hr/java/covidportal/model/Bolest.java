package main.java.hr.java.covidportal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet bolesti
 * koja je definirana simptomima i tome jeli se radi o virusu ili bolesti
 */

public class Bolest extends ImenovaniEntitet implements Serializable {

    private List<Simptom> simptomi;
    private Integer tipBolesti;

    /**
     * Inicijalizira podatak o imptomima i tipu bolesti,a naziv se salje superklasi <code>ImenovaniEntitet</code>
     * @param naziv podatak o nazivu bolesti
     * @param simptomi podatak o simptomima bolesti
     * @param tipBolesti podatak o tipu bolesti
     */
    public Bolest(String naziv, Long id, List<Simptom> simptomi, Integer tipBolesti){
        super(naziv,id);
        this.simptomi = simptomi;
        this.tipBolesti = tipBolesti;



    }
    public List<Simptom> getSymptoms() {
        return simptomi;
    }

    public String getStringSimptomi() {
        String stringSimptomi = "";
        for(int i = 0;i < simptomi.size();i++){
            stringSimptomi = stringSimptomi + simptomi.get(i).getNaziv();
            if(i != (simptomi.size() - 1) ){
                stringSimptomi = stringSimptomi + ",";
            }
        }
        return stringSimptomi;
    }
    public void setSymptoms(List<Simptom> simptoms) {
        this.simptomi = simptoms;
    }

    public Integer getTipBolesti() {
        return tipBolesti;
    }

    public void setTipBolesti(Integer tipBolesti) {
        this.tipBolesti = tipBolesti;
    }




}