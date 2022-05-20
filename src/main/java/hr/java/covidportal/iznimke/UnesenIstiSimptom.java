package main.java.hr.java.covidportal.iznimke;

/**
 * Predstavlja iznimku koja provjerava jeli unesen isti simptom vise puta.
 */

public class UnesenIstiSimptom extends Exception {
    /**
     * Salje poruku o iznimci super klasi <code>Exception</code>
     * @param message poruka o iznimci
     */
    public UnesenIstiSimptom(String message) {
        super(message);
    }
    /**
     * Salje poruku o iznimci i uzroku super klasi <code>Exception</code>
     * @param message poruka o iznimci
     * @param cause uzrok iznimke
     */
    public UnesenIstiSimptom(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Salje uzrok iznimke super klasi <code>Exception</code>
     * @param cause poruka o iznimci
     */
    public UnesenIstiSimptom(Throwable cause) {
        super(cause);
    }
}
