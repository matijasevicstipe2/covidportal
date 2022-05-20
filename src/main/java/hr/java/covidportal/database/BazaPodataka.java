package main.java.hr.java.covidportal.database;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.model.Bolest;
import main.java.hr.java.covidportal.model.Osoba;
import main.java.hr.java.covidportal.model.Simptom;
import main.java.hr.java.covidportal.model.Zupanija;

import javax.print.DocFlavor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.LinkOption;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BazaPodataka {

    private static final String DATABASE_FILE = "src\\main\\resources\\database.properties";
    public static boolean aktivnaVezaSBazomPodataka = false;

    public static synchronized Connection spajanjeSaBazom() throws SQLException, IOException, InterruptedException {

        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
        String korisnickoIme = svojstva.getProperty("korisnickoIme");
        String lozinka = svojstva.getProperty("lozinka");

        Connection veza = DriverManager.getConnection(urlBazePodataka,korisnickoIme,lozinka);

        return veza;
    }

    public static void odspajanjeSaBazom(Connection veza) throws SQLException {
        veza.close();
    }

    public static List<Simptom> dohvatiSimptome() throws SQLException, IOException, InterruptedException {

        List<Simptom> listaSimptoma = new ArrayList<>();
        Connection veza = spajanjeSaBazom();

        Statement upit = veza.createStatement();
        ResultSet setSimptoma = upit.executeQuery("SELECT * FROM SIMPTOM");

        while (setSimptoma.next()){
            Long id = setSimptoma.getLong("ID");
            String naziv = setSimptoma.getString("NAZIV");
            String vrijednost = setSimptoma.getString("VRIJEDNOST");
            VrijednostSimptoma v;
            v = VrijednostSimptoma.valueOf(vrijednost);

            Simptom privSimp = new Simptom(naziv,id,v.getVrijednost());
            listaSimptoma.add(privSimp);
        }
        odspajanjeSaBazom(veza);

        return listaSimptoma;
    }
    public Simptom dohvatiJedanSimptom(Long trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM SIMPTOM WHERE ID = ?");
        upit.setLong(1,trazeniId);
        ResultSet setSimptoma = upit.executeQuery();

        Long id = setSimptoma.getLong("ID");
        String naziv = setSimptoma.getString("NAZIV");
        String vrijednost = setSimptoma.getString("VRIJEDNOST");
        VrijednostSimptoma v;
        v = VrijednostSimptoma.valueOf(vrijednost);

        Simptom privSimp = new Simptom(naziv,id,v.getVrijednost());
        odspajanjeSaBazom(veza);

        return privSimp;

    }
    public static void dodajNoviSimptom(Simptom simptom) throws SQLException, IOException, InterruptedException {

        Connection veza = spajanjeSaBazom();

        PreparedStatement upit = veza.prepareStatement("INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES (?,?)");
        upit.setString(1,simptom.getNaziv());
        upit.setString(2,simptom.getVrijednost());
        upit.executeUpdate();

        odspajanjeSaBazom(veza);
    }

    public static List<Bolest> dohvatiBolesti(Boolean jeliVirus) throws SQLException, IOException, InterruptedException {

        List<Bolest> bolesti = new ArrayList<>();
        Connection veza = spajanjeSaBazom();
        String sql = "";
        if (jeliVirus){
            sql = "SELECT * FROM BOLEST  WHERE VIRUS = TRUE ";
        }else{
            sql = "SELECT * FROM BOLEST  WHERE VIRUS = FALSE ";
        }
        Statement upit = veza.createStatement();
        ResultSet setBolesti = upit.executeQuery(sql);
        Statement upit2 = veza.createStatement();

        List<Simptom> simptomi = dohvatiSimptome();
        while (setBolesti.next()){
            Long id = setBolesti.getLong("ID");
            String naziv = setBolesti.getString("NAZIV");
            //simptomi pojedine bolesti
            List<Simptom> simptomiBolesti = new ArrayList<>();
            ResultSet setSimptomaBolesti = upit2.executeQuery("SELECT * FROM BOLEST_SIMPTOM");
            while(setSimptomaBolesti.next()){
                if(id == setSimptomaBolesti.getLong("BOLEST_ID")){
                    for(Simptom simptom : simptomi){
                        if(simptom.getId() == setSimptomaBolesti.getLong("SIMPTOM_ID")){
                            simptomiBolesti.add(simptom);
                        }
                    }
                }
            }
            Integer tipBolesti = 0;
            if (jeliVirus){
                tipBolesti = 2;
            }else{
                tipBolesti = 1;
            }
            Bolest bolest = new Bolest(naziv,id,simptomiBolesti,tipBolesti);
            bolesti.add(bolest);
        }

        odspajanjeSaBazom(veza);

        return bolesti;
    }
    public static List<Bolest> dohvatiSveBolesti() throws SQLException, IOException, InterruptedException {

        List<Bolest> bolesti = new ArrayList<>();
        Connection veza = spajanjeSaBazom();

        Statement upit = veza.createStatement();
        ResultSet setBolesti = upit.executeQuery("SELECT * FROM BOLEST");
        Statement upit2 = veza.createStatement();

        List<Simptom> simptomi = dohvatiSimptome();
        while (setBolesti.next()){
            Long id = setBolesti.getLong("ID");
            String naziv = setBolesti.getString("NAZIV");
            Boolean virus = setBolesti.getBoolean("VIRUS");
            //simptomi pojedine bolesti
            List<Simptom> simptomiBolesti = new ArrayList<>();
            ResultSet setSimptomaBolesti = upit2.executeQuery("SELECT * FROM BOLEST_SIMPTOM");
            while(setSimptomaBolesti.next()){
                if(id == setSimptomaBolesti.getLong("BOLEST_ID")){
                    for(Simptom simptom : simptomi){
                        if(simptom.getId() == setSimptomaBolesti.getLong("SIMPTOM_ID")){
                            simptomiBolesti.add(simptom);
                        }
                    }
                }
            }
            Integer tipBolesti = 0;
            if (virus){
                tipBolesti = 2;
            }else{
                tipBolesti = 1;
            }
            Bolest bolest = new Bolest(naziv,id,simptomiBolesti,tipBolesti);
            bolesti.add(bolest);
        }

        odspajanjeSaBazom(veza);

        return bolesti;
    }

    public static Bolest dohvatiJednuBolest(Long trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM BOLEST WHERE ID = ?");
        upit.setLong(1,trazeniId);
        ResultSet setBolesti = upit.executeQuery();
        Bolest bolest = null;
        List<Simptom> simptomi = dohvatiSimptome();
        while(setBolesti.next()){
            Long id = setBolesti.getLong("ID");
            String naziv = setBolesti.getString("NAZIV");
            Boolean virus = setBolesti.getBoolean("VIRUS");

            List<Simptom> simptomiBolesti = new ArrayList<>();
            Statement upit2 = veza.createStatement();
            ResultSet setSimptomaBolesti = upit2.executeQuery("SELECT * FROM BOLEST_SIMPTOM");
            while(setSimptomaBolesti.next()){
                if(id == setSimptomaBolesti.getLong("BOLEST_ID")){
                    for(Simptom simptom : simptomi){
                        if(simptom.getId() == setSimptomaBolesti.getLong("SIMPTOM_ID")){
                            simptomiBolesti.add(simptom);
                        }
                    }
                }
            }
            Integer tipBolesti = 0;
            if (virus){
                tipBolesti = 2;
            }else{
                tipBolesti = 1;
            }
             bolest = new Bolest(naziv,id,simptomiBolesti,tipBolesti);
        }
        odspajanjeSaBazom(veza);
         return bolest;
    }

    public static void dodajNovuBolest(TextField t1, ObservableList<String> obsList,
                                       Boolean jeliVirus) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();

        PreparedStatement upit = veza.prepareStatement
                ("INSERT INTO BOLEST(NAZIV,VIRUS) VALUES(?,?)",Statement.RETURN_GENERATED_KEYS);


        upit.setString(1,t1.getText());
        upit.setBoolean(2,jeliVirus);
        upit.executeUpdate();

        ResultSet resultSet = upit.getGeneratedKeys();
        Long id = 0L;
        while(resultSet.next()){
            id = resultSet.getLong(1);
        }
        PreparedStatement upit2 = veza.prepareStatement
                ("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES(?,?)");
        List<Simptom> simptomi = dohvatiSimptome();
        for(Simptom s : simptomi){
            for(String naziv : obsList){
                if(naziv.equals(s.getNaziv())){
                    upit2.setLong(1,id);
                    upit2.setLong(2, s.getId());
                    upit2.executeUpdate();
                }
            }
        }

        odspajanjeSaBazom(veza);
    }

    public static List<Zupanija> dohvatiZupanije() throws SQLException, IOException, InterruptedException {
        List<Zupanija> listaZupanija = new ArrayList<>();
        Connection veza = spajanjeSaBazom();

        Statement upit = veza.createStatement();
        ResultSet setZupanija = upit.executeQuery("SELECT * FROM ZUPANIJA");

        while (setZupanija.next()){
            Long id = setZupanija.getLong("ID");
            String naziv = setZupanija.getString("NAZIV");
            Integer brStan = setZupanija.getInt("BROJ_STANOVNIKA");
            Integer brZarazStan = setZupanija.getInt("BROJ_ZARAZENIH_STANOVNIKA");

            Zupanija zupanija = new Zupanija(naziv,id,brStan,brZarazStan);
            listaZupanija.add(zupanija);

        }
        odspajanjeSaBazom(veza);
        return listaZupanija;
    }
    public static Zupanija dohvatiJednuZupaniju( Long trazeniId) throws SQLException, IOException, InterruptedException {

        Connection veza = spajanjeSaBazom();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM ZUPANIJA WHERE ID = ?");
        upit.setLong(1,trazeniId);
        ResultSet setZupanija = upit.executeQuery();
        Zupanija zupanija = null;
        while(setZupanija.next()){
            Long id = setZupanija.getLong("ID");
            String naziv = setZupanija.getString("NAZIV");
            Integer brStan = setZupanija.getInt("BROJ_STANOVNIKA");
            Integer brZarazStan = setZupanija.getInt("BROJ_ZARAZENIH_STANOVNIKA");
            zupanija = new Zupanija(naziv,id,brStan,brZarazStan);
        }

        odspajanjeSaBazom(veza);
        return zupanija;

    }
    public static void dodajNovuZupaniju(TextField t1,TextField t2,TextField t3) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();

        PreparedStatement upit = veza.prepareStatement("INSERT INTO" +
                " ZUPANIJA(NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA) " +
                "VALUES(?,?,?)");
        upit.setString(1,t1.getText());
        upit.setInt(2,Integer.parseInt(t2.getText()));
        upit.setInt(3,Integer.parseInt(t3.getText()));

        upit.executeUpdate();
        odspajanjeSaBazom(veza);
    }

    public static List<Osoba> dohvatiOsobe() throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();
        List<Osoba> osobe = new ArrayList<>();
        Statement upit = veza.createStatement();
        ResultSet setOsoba = upit.executeQuery("SELECT * FROM OSOBA");

        while(setOsoba.next()){
            Long id = setOsoba.getLong("ID");
            String ime = setOsoba.getString("IME");
            String prezime = setOsoba.getString("PREZIME");

            Date date = setOsoba.getDate("DATUM_RODJENJA");
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(
                    instant, ZoneId.systemDefault()).toLocalDate();

            Long years = ChronoUnit.YEARS.between(localDate, LocalDate.now());

            Long zupanijaID = setOsoba.getLong("ZUPANIJA_ID");
            Long bolestID = setOsoba.getLong("BOlEST_ID");

            Bolest bolest = dohvatiJednuBolest(bolestID);
            Zupanija zupanija = dohvatiJednuZupaniju(zupanijaID);

            osobe.add(new Osoba.Builder(ime,prezime,years,id)
                    .zup(zupanija)
                    .zaraBol(bolest)
                    .kon(null)
                    .build());

        }
        Statement upit2 = veza.createStatement();


        for(Osoba o : osobe){
            List<Osoba> kontakti = new ArrayList<>();
            ResultSet setKontakata = upit2.executeQuery("SELECT * FROM KONTAKTIRANE_OSOBE");
            while(setKontakata.next()){
                Long osobaID = setKontakata.getLong("OSOBA_ID");
                if(o.getId() == osobaID){
                    Long kontaktID = setKontakata.getLong("KONTAKTIRANA_OSOBA_ID");
                    Osoba osoba = dohvatiJednuOsobu(kontaktID);
                    kontakti.add(osoba);
                }
            }
            o.setKontakti(kontakti);
        }

        odspajanjeSaBazom(veza);
        return osobe;
    }

    public static Osoba dohvatiJednuOsobu(Long trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM OSOBA WHERE ID = ?");
        upit.setLong(1,trazeniId);
        ResultSet setOsoba = upit.executeQuery();

        Osoba osoba = null;

        while(setOsoba.next()){
            Long id = setOsoba.getLong("ID");
            String ime = setOsoba.getString("IME");
            String prezime = setOsoba.getString("PREZIME");

            Date date = setOsoba.getDate("DATUM_RODJENJA");
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(
                    instant, ZoneId.systemDefault()).toLocalDate();

            Long years = ChronoUnit.YEARS.between(localDate, LocalDate.now());

            Long zupanijaID = setOsoba.getLong("ZUPANIJA_ID");
            Long bolestID = setOsoba.getLong("BOlEST_ID");

            Bolest bolest = dohvatiJednuBolest(bolestID);
            Zupanija zupanija = dohvatiJednuZupaniju(zupanijaID);

            osoba = new Osoba.Builder(ime,prezime,years,id)
                    .zup(zupanija)
                    .zaraBol(bolest)
                    .kon(null)
                    .build();
        }

        odspajanjeSaBazom(veza);
        return osoba;

    }

    public static void dodajNovuOsobu(TextField t1, TextField t2,
                                      DatePicker datePicker,
                                      ObservableList<String> obsZupanija,
                                      ObservableList<String> obsBolest,
                                      ObservableList<String> obsKontakt ) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();
        PreparedStatement upit = veza.prepareStatement("INSERT INTO " +
                "OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID) " +
                "VALUES (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
        upit.setString(1,t1.getText());
        upit.setString(2,t2.getText());
        upit.setDate(3, Date.valueOf(datePicker.getValue()));

        List<Zupanija> zupanije = new ArrayList<>();
        zupanije = dohvatiZupanije();
        Long idOdZupanije = 0L;
        for(String s : obsZupanija){
            System.out.println(1);
            for(Zupanija z : zupanije){
                System.out.println(2);
                if(z.getNaziv().equals(s)){
                    System.out.println(3);
                    idOdZupanije = z.getId();
                }
            }
        }
        upit.setLong(4,idOdZupanije);
        Long idOdBolesti = 0L;
        List<Bolest> bolesti = new ArrayList<>();
        bolesti = dohvatiSveBolesti();
        for(String s : obsBolest){
            for(Bolest b : bolesti){
                if(b.getNaziv().equals(s)){
                    idOdBolesti = b.getId();
                }
            }
        }
        System.out.println(idOdBolesti + " " +idOdZupanije);
        upit.setLong(5,idOdBolesti);
        upit.executeUpdate();
        ResultSet set = upit.getGeneratedKeys();
        Long id = 0L;
        while(set.next()){
            id = set.getLong(1);
        }

        PreparedStatement upit2 = veza.prepareStatement("INSERT " +
                "INTO KONTAKTIRANE_OSOBE VALUES(?,?)");
        List<Osoba> osobe = new ArrayList<>();
        osobe = dohvatiOsobe();
        for(Osoba o : osobe){
            for(String s : obsKontakt){
                if(o.getIme().equals(s)){
                    upit2.setLong(1,id);
                    upit2.setLong(2,o.getId());
                    upit2.executeUpdate();
                }
            }
        }

        odspajanjeSaBazom(veza);
    }
    //drugi zadatak labos
    public static List<Osoba> dohvatiOsobeIzZupanije(Long trazeniId) throws IOException, SQLException, InterruptedException {
        Connection veza = spajanjeSaBazom();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM OSOBA WHERE ID = ?");
        upit.setLong(1,trazeniId);
        ResultSet setOsoba = upit.executeQuery();

        List<Osoba> osobe = new ArrayList<>();

        while(setOsoba.next()){
            Long id = setOsoba.getLong("ID");
            String ime = setOsoba.getString("IME");
            String prezime = setOsoba.getString("PREZIME");

            Date date = setOsoba.getDate("DATUM_RODJENJA");
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate localDate = LocalDateTime.ofInstant(
                    instant, ZoneId.systemDefault()).toLocalDate();

            Long years = ChronoUnit.YEARS.between(localDate, LocalDate.now());

            Long zupanijaID = setOsoba.getLong("ZUPANIJA_ID");
            Long bolestID = setOsoba.getLong("BOlEST_ID");

            Bolest bolest = dohvatiJednuBolest(bolestID);
            Zupanija zupanija = dohvatiJednuZupaniju(zupanijaID);

            Osoba osoba = new Osoba.Builder(ime,prezime,years,id)
                    .zup(zupanija)
                    .zaraBol(bolest)
                    .kon(null)
                    .build();
            osobe.add(osoba);
        }

        odspajanjeSaBazom(veza);
        return osobe;

    }
    public static void obrisiZupaniju(Long trazeniId) throws SQLException, IOException, InterruptedException {
        Connection veza = spajanjeSaBazom();

        PreparedStatement prviUpit = veza.prepareStatement("DELETE FROM KONTAKTIRANE_OSOBE\n" +
                "\n" +
                "WHERE OSOBA_ID IN\n" +
                "\n" +
                "(SELECT ID FROM OSOBA WHERE ZUPANIJA_ID = ?)\n" +
                "\n" +
                "OR KONTAKTIRANA_OSOBA_ID IN\n" +
                "\n" +
                "(SELECT ID FROM OSOBA WHERE ZUPANIJA_ID = ?);");

        prviUpit.setLong(1, trazeniId);
        prviUpit.setLong(2, trazeniId);
        prviUpit.executeUpdate();

        PreparedStatement drugiUpit = veza.prepareStatement("DELETE FROM OSOBA WHERE ZUPANIJA_ID = ?;");
        drugiUpit.setLong(1, trazeniId);
        drugiUpit.executeUpdate();

        PreparedStatement treciUpit = veza.prepareStatement("DELETE FROM zupanija WHERE ID = ?;");
        treciUpit.setLong(1, trazeniId);
        treciUpit.executeUpdate();
        odspajanjeSaBazom(veza);
    }
    //prvi zadatak labos
    public static int dohvatiCountSimptoma() throws InterruptedException, SQLException, IOException {
        Connection veza = spajanjeSaBazom();
        PreparedStatement upit = veza.prepareStatement("SELECT COUNT(*) AS broj FROM SIMPTOM;");
        int broj = 0;
        ResultSet setCount = upit.executeQuery();
        while(setCount.next()){
             broj = setCount.getInt(1);
        }
        odspajanjeSaBazom(veza);
        return broj;
    }
    //drugi zadatak labos
    public static Simptom zadnjiUneseniSimptom() throws InterruptedException, SQLException, IOException {
        Connection veza = spajanjeSaBazom();
        PreparedStatement upit = veza.prepareStatement("SELECT *\n" +
                "\n" +
                "FROM SIMPTOM\n" +
                "\n" +
                "ORDER BY ID DESC\n" +
                "\n" +
                "LIMIT 1;");
        ResultSet setSimptoma = upit.executeQuery();
        Simptom trazeniSimptom = null;
        while(setSimptoma.next()){
            Long id = setSimptoma.getLong("ID");
            String naziv = setSimptoma.getString("NAZIV");
            String vrijednost = setSimptoma.getString("VRIJEDNOST");
            trazeniSimptom = new Simptom(naziv,id,vrijednost);
        }
        odspajanjeSaBazom(veza);
        return trazeniSimptom;
    }

}
