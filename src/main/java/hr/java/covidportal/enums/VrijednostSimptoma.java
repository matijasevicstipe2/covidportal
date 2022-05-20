package main.java.hr.java.covidportal.enums;

public enum VrijednostSimptoma {
    Produktivni("Produktivni"),
    Intenzivno("Intenzivno"),
    Visoka("Visoka"),
    Jaka("Jaka");

    private String vrijednost;

    private VrijednostSimptoma(String vrijednost){
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }
}
