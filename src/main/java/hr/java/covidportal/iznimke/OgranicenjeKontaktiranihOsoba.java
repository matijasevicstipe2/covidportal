package main.java.hr.java.covidportal.iznimke;

/**
 * Predstavlja iznimku koja provjerava jeli za broj kotakata unesen smislen izbor.
 * Primjer: izbor ne moze biti negativan broj ili ako unosimo podatke za trecu osobu
 * ona moze imati najvise 2 kontakta!
 */

public class OgranicenjeKontaktiranihOsoba extends Exception {
    /**
     * Salje poruku o iznimci super klasi <code>Exception</code>
     * @param message poruka o iznimci
     */
    public OgranicenjeKontaktiranihOsoba(String message) {
        super(message);
    }
    /**
     * Salje poruku o iznimci i uzroku super klasi <code>Exception</code>
     * @param message poruka o iznimci
     * @param cause uzrok iznimke
     */
    public OgranicenjeKontaktiranihOsoba(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Salje uzrok iznimke super klasi <code>Exception</code>
     * @param cause poruka o iznimci
     */
    public OgranicenjeKontaktiranihOsoba(Throwable cause) {
        super(cause);
    }
}
