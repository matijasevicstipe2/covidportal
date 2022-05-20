package main.java.hr.java.covidportal.main;

import main.java.hr.java.covidportal.enums.VrijednostSimptoma;
import main.java.hr.java.covidportal.genericsi.KlinikaZaInfektivneBolesti;
import main.java.hr.java.covidportal.model.*;
import main.java.hr.java.covidportal.sort.CovidSorter;

import java.io.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Glavna {


    public static void main(String[] args) {

        Scanner unos = new Scanner(System.in);
        List<Zupanija> zupanije;
        List<Simptom> simptomi;
        List<Bolest> bolesti;
        List<Osoba> osobe;
        KlinikaZaInfektivneBolesti<Virus,Osoba> klinika = new KlinikaZaInfektivneBolesti<>();

        //zupanije
        zupanije = datotekaZupanija();


        //simptomi
        simptomi = datotekaSimptomi();

        //bolesti i virusi
        bolesti = datotekaBolesti(simptomi);
        bolesti.addAll(datotekaVirusi(simptomi));

        //osobe
        osobe = datotekaOsobe(zupanije,bolesti);

        // popis osoba
        System.out.println("Popis osoba:");

        for(int i = 0; i < osobe.size(); i++){
            System.out.print("Ime i prezime: ");
            System.out.println(osobe.get(i).getIme() +" "+osobe.get(i).getPrezime());
            System.out.print("Starost: ");
            System.out.println(osobe.get(i).getGodine());
            System.out.print("Županija: ");
            System.out.println(osobe.get(i).getZupanija().getNaziv());
            System.out.print("Zaražen bolešću: ");
            System.out.println(osobe.get(i).getZarazenBolescu().getNaziv());
            System.out.println("Kontaktirane osobe: ");
            if(i == 0){
                System.out.println("Nema kontaktiranih osoba.");
            }
            else{
                for(int j = 0; j < osobe.get(i).getKontakti().size(); j++){
                    System.out.println(osobe.get(i).getKontakti().get(j).getIme()+"  "+osobe.get(i).getKontakti().get(j).getPrezime());
                }
            }

        }

        Map<Bolest,List<Osoba>> mapaZarazenosti;
        mapaZarazenosti = mapaOsobaSaNjihovimBolestima(bolesti,osobe);
        for (Map.Entry<Bolest,List<Osoba>> entry : mapaZarazenosti.entrySet()) {
            if(entry.getValue().size() == 0){
                System.out.println("Nitko nije zaražen sa " + entry.getKey().getNaziv());
            }else{
                System.out.println("Od bolesti/virusa "+entry.getKey().getNaziv() + " boluje ");
                for(int i = 0;i < entry.getValue().size();i++){
                    System.out.print(entry.getValue().get(i).getIme() + " " + entry.getValue().get(i).getPrezime());

                }
                System.out.println();
            }

        }
        CovidSorter covidSorter = new CovidSorter();
        for(Zupanija z1 : zupanije){
            for(Zupanija z2 : zupanije){
                covidSorter.compare(z1,z2);
            }
        }
        System.out.println("Županija sa najviše zaraženih je " + zupanije.get(0).getNaziv() + " sa postotkom "
                + zupanije.get(0).getPostotak());
        //2.ZADATAK
        klinika.setListaOsobaSaVirusom(osobe.stream()
                .filter(osoba -> osoba.getZarazenBolescu() instanceof Virus)
                .collect(Collectors.toList()));

        //3.ZADATAK
        Instant start = Instant.now();
        klinika.setListaVirusa(klinika.getListaVirusa().stream()
                .sorted(Comparator.comparing((Virus v) -> v.getNaziv()).reversed())
                .collect(Collectors.toList()));
        Instant end = Instant.now();

        System.out.println("Virusi sortirani po nazivu suprotno od poretka abecede:");
        klinika.getListaVirusa().stream().map(virus -> virus.getNaziv()).forEach(System.out::println);

        //4.ZADATAK
        List<Virus> pomVirusi = new ArrayList<>();
        for(Bolest bolest : bolesti){
            if(bolest instanceof Virus virus){
                pomVirusi.add(virus);
            }
        }
        Instant start2 = Instant.now();
        for(int i = 0;i < pomVirusi.size();i++){
            for(int j = 0;j < pomVirusi.size()-1;j++){
                if(pomVirusi.get(j).getNaziv().charAt(0) > pomVirusi.get(j+1).getNaziv().charAt(0)){
                    Virus z = pomVirusi.get(j);
                    pomVirusi.set(j,pomVirusi.get(j+1));
                    pomVirusi.set(j+1,z);
                }
            }
        }
        Instant end2 = Instant.now();

        System.out.println("Sortiranje objekata korištenjem lambdi traje " + Duration.between(start,end).toMillis()
                + " milisekundi, a bez lambda traje " + Duration.between(start2,end2).toMillis()
                +" milisekundi. ");

        //5.ZADATAK OPTIONAL
        System.out.print("Unesite string za pretragu po prezimenu: ");
        String string = unos.nextLine();
        Optional<List<Osoba>> optionalOsoba = Optional.of(osobe.stream()
                .filter(osoba -> osoba.getPrezime().contains(string))
                .collect(Collectors.toList()));

        if(optionalOsoba.get().isEmpty()){
            System.out.println("Nema prezimena sa zadanim stringom!");
        }
        else{
            for(Osoba osoba : optionalOsoba.get()){
                System.out.println(osoba.getIme() + " " + osoba.getPrezime());
            }
        }
        //6.ZADATAKK
        bolesti.stream()
                .map(bolest -> bolest.getNaziv() +" ima " + bolest.getSymptoms().size() + " simptoma.")
                .forEach(System.out::println);


        try(ObjectOutputStream serializ = new ObjectOutputStream(new FileOutputStream("dat/zupanije.dat"))){
            for(Zupanija z : zupanije){
                if (z.getPostotak() >= 2){
                    serializ.writeObject(z);
                }
            }

        }catch (IOException e){
            e.printStackTrace();

        }

    }

    private static List<Simptom> dohvatiSimptome(String pomocni,List<Simptom> simptomi){
        List<Simptom> pomSimptomi = new ArrayList<>();
        int i = 0;
        while(i < pomocni.length()){
            Long c = (long) pomocni.charAt(i) - 48;
            for(Simptom s : simptomi) {
                if (s.getId() == c) {
                    pomSimptomi.add(s);
                }
            }
            i = i + 2;
        }

        return pomSimptomi;
    }

    private static Zupanija dohvatiZupaniju(String zupanijaString,List<Zupanija> zupanije){
        Zupanija pomZupanija = null;
        for(Zupanija z : zupanije){
            if(z.getNaziv().equals(zupanijaString)){
                pomZupanija = z;
            }
        }

        return pomZupanija;
    }
    private static Bolest dohvatiBolest(String bolestString,List<Bolest> bolesti){
        Bolest pomBolest = null;
        for(Bolest b : bolesti){
            if(b.getNaziv().equals(bolestString)){
                pomBolest = b;
            }
        }
        return  pomBolest;
    }
    private static Map<Bolest,List<Osoba>> mapaOsobaSaNjihovimBolestima(List<Bolest> bolesti,List<Osoba> osobe){
        Map<Bolest,List<Osoba>> mapaZarazenosti = new HashMap<>();
        Bolest[] pomBolesti = new Bolest[bolesti.size()];
        bolesti.toArray(pomBolesti);

        for(Osoba osoba : osobe){
            for(int i = 0;i < pomBolesti.length;i++){
                if(mapaZarazenosti.containsKey(pomBolesti[i])){
                    if(osoba.getZarazenBolescu().getNaziv().equals(pomBolesti[i].getNaziv())){
                        mapaZarazenosti.get(pomBolesti[i]).add(osoba);
                    }
                }else {
                    List<Osoba> pomOsobe = new ArrayList<>();
                    if(osoba.getZarazenBolescu().getNaziv().equals(pomBolesti[i].getNaziv())){
                        pomOsobe.add(osoba);
                    }
                    mapaZarazenosti.put(pomBolesti[i],pomOsobe);
                }
            }
        }
        return mapaZarazenosti;
    }
    public static List<Zupanija> datotekaZupanija(){
        List<Zupanija> zupanije = new ArrayList<>();
        File zup = new File("dat/zupanije.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(zup))){

            String linija;
            while((linija = bufferedReader.readLine()) != null){
                Long id = Long.parseLong(linija);
                String naziv = bufferedReader.readLine();
                Integer brStan = Integer.parseInt(bufferedReader.readLine());
                Integer brZaraz = Integer.parseInt(bufferedReader.readLine());
                zupanije.add(new Zupanija(naziv,id,brStan,brZaraz));
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return zupanije;

    }
    public static List<Simptom> datotekaSimptomi(){
        List<Simptom> simptomi = new ArrayList<>();
        File sim = new File("dat/simptomi.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sim))){

            String linija;
            while((linija = bufferedReader.readLine()) != null){
                String naziv = linija;
                Long id = Long.parseLong(bufferedReader.readLine());
                String vrijednost = bufferedReader.readLine();
                VrijednostSimptoma v;
                v = VrijednostSimptoma.valueOf(vrijednost);
                simptomi.add(new Simptom(naziv,id,v.getVrijednost()));
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return simptomi;
    }
    public static List<Bolest> datotekaBolesti(List<Simptom> simptomi){
        List<Bolest> bolesti = new ArrayList<>();
        File bol = new File("dat/bolesti.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(bol))){

            String linija;
            while((linija = bufferedReader.readLine()) != null){
                Long id = Long.parseLong(linija);
                String naziv = bufferedReader.readLine();
                String pomocni = bufferedReader.readLine();
                List<Simptom> simptoms = dohvatiSimptome(pomocni,simptomi);
                Integer tip = Integer.parseInt(bufferedReader.readLine());
                bolesti.add(new Bolest(naziv,id,simptoms,tip));

            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return bolesti;
    }
    public static List<Virus> datotekaVirusi(List<Simptom> simptomi){
        List<Virus> virusi = new ArrayList<>();
        File vir = new File("dat/virusi.txt");
        try (BufferedReader bufferedReader2 = new BufferedReader(new FileReader(vir))){

            String linija;
            while ((linija = bufferedReader2.readLine()) != null){
                Long idV = Long.parseLong(linija);
                String nazivV = bufferedReader2.readLine();
                String pomocni = bufferedReader2.readLine();
                List<Simptom> simptomsV = dohvatiSimptome(pomocni,simptomi);
                bufferedReader2.readLine();
                virusi.add(new Virus(nazivV,idV,simptomsV));

            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return virusi;

    }
    public static List<Osoba> datotekaOsobe(List<Zupanija> zupanije,List<Bolest> bolesti){
        List<Osoba> osobe = new ArrayList<>();
        File oso = new File("dat/osobe.txt");
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(oso))){

            String linija;
            int i = 0;
            while ((linija = bufferedReader.readLine()) != null){
                String ime = linija;
                String prezime = bufferedReader.readLine();
                Long godine = Long.parseLong(bufferedReader.readLine());
                Long id = Long.parseLong(bufferedReader.readLine());
                String pomocni = bufferedReader.readLine();
                Zupanija z = dohvatiZupaniju(pomocni,zupanije);
                pomocni = bufferedReader.readLine();
                Bolest b = dohvatiBolest(pomocni,bolesti);
                if(i != 0){
                    List<Osoba> kontaktiraneOsobe = new ArrayList<>();
                    String osobeString = bufferedReader.readLine();
                    List<String> kontakti = Arrays.asList(osobeString.split(","));
                    for(String s : kontakti){
                        for(Osoba o : osobe){
                            if( s.equals(o.getIme())){
                                kontaktiraneOsobe.add(o);
                            }
                        }
                    }
                    osobe.add(new Osoba.Builder(ime,prezime,godine,id)
                            .zup(z)
                            .zaraBol(b)
                            .kon(kontaktiraneOsobe)
                            .build());
                }
                else{
                    osobe.add(new Osoba.Builder(ime,prezime,godine,id)
                            .zup(z)
                            .zaraBol(b)
                            .kon(null)
                            .build());
                }

                i++;

            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return osobe;
    }

}
