package main.java.hr.java.covidportal.iznimke;

/**
 * Predstavlja iznimku koja provjerava jeli unesen isti kontakt dvaput.
 */

public class DuplikatKontaktiraneOsobe extends Exception {
    /**
     * Salje poruku o iznimci super klasi <code>Exception</code>
     * @param message poruka o iznimci
     */

    public DuplikatKontaktiraneOsobe(String message) {
        super(message);
    }
    /**
     * Salje poruku o iznimci i uzroku super klasi <code>Exception</code>
     * @param message poruka o iznimci
     * @param cause uzrok iznimke
     */
    public DuplikatKontaktiraneOsobe(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Salje uzrok iznimke super klasi <code>Exception</code>
     * @param cause poruka o iznimci
     */
    public DuplikatKontaktiraneOsobe(Throwable cause) {
        super(cause);
    }
}
