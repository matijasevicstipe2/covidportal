package main.java.hr.java.covidportal.iznimke;

/**
 * Predstavlja iznimku koja provjerava jeli pri odabiru kontakata unesen smislen broj.
 */

public class OgranicenjeZaOdabirKontakata  extends Exception{
    /**
     * Salje poruku o iznimci super klasi <code>Exception</code>
     * @param message poruka o iznimci
     */
    public OgranicenjeZaOdabirKontakata(String message) {
        super(message);
    }
    /**
     * Salje poruku o iznimci i uzroku super klasi <code>Exception</code>
     * @param message poruka o iznimci
     * @param cause uzrok iznimke
     */
    public OgranicenjeZaOdabirKontakata(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Salje uzrok iznimke super klasi <code>Exception</code>
     * @param cause poruka o iznimci
     */
    public OgranicenjeZaOdabirKontakata(Throwable cause) {
        super(cause);
    }
}
