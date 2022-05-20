package main.java.hr.java.covidportal.iznimke;

/**
 * Predstavlja iznimku koja provjerava jeli unesena bolest sa istim simptomima kao neka bolest prije nje.
 */

public class BolestIstihSimptoma  extends Exception{
    /**
     * Salje poruku o iznimci super klasi <code>Exception</code>
     * @param message poruka o iznimci
     */
    public BolestIstihSimptoma(String message) {
        super(message);
    }
    /**
     * Salje poruku o iznimci i uzroku super klasi <code>Exception</code>
     * @param message poruka o iznimci
     * @param cause uzrok iznimke
     */
    public BolestIstihSimptoma(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Salje uzrok iznimke super klasi <code>Exception</code>
     * @param cause poruka o iznimci
     */
    public BolestIstihSimptoma(Throwable cause) {
        super(cause);
    }
}
