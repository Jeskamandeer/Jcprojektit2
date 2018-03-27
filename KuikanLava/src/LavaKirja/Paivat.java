package LavaKirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;
import kanta.SailoException;
/**
 * 
 * @author Jere
 * @version 21.6.2017
 *
 */
public class Paivat implements Iterable<Paiva> {
    
    private static final int MAX_PaivaIA   = 15;
    private boolean muutettu = false;
    private int              lkm           = 0;
    private String kokoNimi = "Paivat";
    private String tiedostonPerusNimi = "Paivat";   
    private Paiva            alkiot[]      = new Paiva[MAX_PaivaIA];

    /**
     * Oletusmuodostaja
     */
    public Paivat() {
        // Attribuuttien oma alustus riitt‰‰
    }

    /**
     * Lis‰‰ uuden p‰iv‰n tietorakenteeseen.  Ottaa p‰iv‰n omistukseensa.
     * @param paiva lis‰t‰‰v‰n p‰iv‰n viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t‰ynn‰
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import kanta.SailoException;
     * Paivat paivat = new Paivat();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
     * paivat.getLkm() === 0;
     * paivat.lisaa(paiva1); paivat.getLkm() === 1;
     * paivat.lisaa(paiva2); paivat.getLkm() === 2;
     * paivat.lisaa(paiva1); paivat.getLkm() === 3;
     * Iterator<Paiva> it = paivat.iterator();
     * it.next() === paiva1;
     * it.next() === paiva2;
     * it.next() === paiva1;
     * paivat.lisaa(paiva1); paivat.getLkm() === 4;
     * paivat.lisaa(paiva1); paivat.getLkm() === 5;
     * </pre>
     */
    public void lisaa(Paiva paiva) throws SailoException {      
        if ( lkm >= alkiot.length ) alkiot = Arrays.copyOf(alkiot, lkm+1); 
        alkiot[lkm] = paiva;
        lkm++;
        muutettu = true; 
    }


    /**
     * Palauttaa viitteen i:teen p‰iv‰‰n.
     * @param i monennenko p‰iv‰n viite halutaan
     * @return viite p‰iv‰‰n, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Paiva anna(int i) throws IndexOutOfBoundsException {
        
        if ( i < 0 || lkm <= i ) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }


    /**
     * Lukee p‰iv‰t tiedostosta.
     * @param tied Tiedosto
     * @throws SailoException jos lukeminen ep‰onnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * #import kanta.SailoException;
     *
     *  Paivat paivat = new Paivat();
     *  Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
     *  paiva1.vastaaPaivan();
     *  paiva2.vastaaPaivan();
     *  String hakemisto = "testipaivat";
     *  String tiedNimi = hakemisto+"/paivat";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  paivat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  paivat.lisaa(paiva1);
     *  paivat.lisaa(paiva2);
     *  paivat.tallenna();
     *  paivat = new Paivat();            
     *  paivat.lueTiedostosta(tiedNimi);  
     *  Iterator<Paiva> i = paivat.iterator();
     *  i.next() === paiva1;
     *  i.next() === paiva2;
     *  i.hasNext() === false;
     *  paivat.lisaa(paiva2);
     *  paivat.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi;
            
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Paiva paiva = new Paiva();
                paiva.parse(rivi); 
                lisaa(paiva);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
    /**
     * Kutsu tiedoston lukemiselle
     * @throws SailoException Jos tulee virhe
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
   /**
    * Hakee tiedoston kokonimen
    * @return kokonimen
    */
    public String getKokoNimi() {
        return kokoNimi;
    }
    /**
     * Hakee tiedoston perusnimen
     * @return tiedoston perusnimen
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    /**
     * Asettaa tiedostolle perusnimen
     * @param nimi joka asetetaan
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    /**
     * Hakee tiedoston nimen
     * @return tiedoston nimi dat muodossa
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    /**
     * Hakee nimen bak muodossa
     * @return nimen bak muodossa
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Tallentaa p‰iv‰t tiedostoon.  Kesken.
     * @throws SailoException jos talletus ep‰onnistuu
     * <pre>
     * 2|10.3.1996|123|123|123|123|123|123|123|
     * 3|30.3.1996|1253|1253|5123|1235|1253|5123|1523|    
     * </pre>
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); 
        ftied.renameTo(fbak); 
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Paiva paiva : this) {
                fo.println(paiva.toString());
            }
            
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }


    /**
     * Palauttaa p‰ivien lukum‰‰r‰n
     * @return p‰ivien lukum‰‰r‰
     */
    public int getLkm() {
        return lkm;
    }
    /**
     * Itetraattori p‰iville 
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * #import kanta.SailoException;
     *
     * Paivat paivat = new Paivat();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
     * paiva1.rekisteroi(); paiva2.rekisteroi();
     *
     * paivat.lisaa(paiva1);
     * paivat.lisaa(paiva2);
     * paivat.lisaa(paiva1);
     *
     * StringBuffer ids = new StringBuffer(30);
     * for (Paiva paiva:paivat)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+paiva.getTunnusNro());           
     *
     * String tulos = " " + paiva1.getTunnusNro() + " " + paiva2.getTunnusNro() + " " + paiva1.getTunnusNro();
     *
     * ids.toString() === tulos;
     *
     * ids = new StringBuffer(30);
     * for (Iterator<Paiva>  i=paivat.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Paiva paiva = i.next();
     *   ids.append(" "+paiva.getTunnusNro());           
     * }
     *
     * ids.toString() === tulos;
     *
     * Iterator<Paiva>  i=paivat.iterator();
     * i.next() == paiva1  === true;
     * i.next() == paiva2  === true;
     * i.next() == paiva1  === true;
     *
     * i.next();  #THROWS NoSuchElementException
     * 
     * </pre>
     *
     */
    public class PaivatIterator implements Iterator<Paiva> {
        private int kohdalla = 0;
   /**
    * Onko olemassa viel‰ seuraavaa p‰iv‰‰
    * @see java.util.Iterator#hasNext()
    * @return true jos on viel‰ p‰ivi‰
    */
      @Override
      public boolean hasNext() {
          return kohdalla < getLkm();
        }
        /**
         * Annetaan seuraava p‰iv‰
         * @return seuraava p‰iv‰
         * @throws NoSuchElementException jos seuraava alkiota ei en‰‰ ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Paiva next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }
        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    /**
     * Palautetaan iteraattori p‰ivist‰‰n.
     * @return p‰iv‰n iteraattori
     */
    @Override
    public Iterator<Paiva> iterator() {
        return new PaivatIterator();
    }
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien p‰ivien viitteet
     * @param hakuehto hakuehto
     * @param k etsitt‰v‰n kent‰n indeksi 
     * @return tietorakenteen lˆytyneist‰ p‰ivist‰
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import kanta.SailoException;
     *   Paivat paivat = new Paivat();
     *   Paiva paiva1 = new Paiva(); paiva1.parse("1|12.3.2015|500|500|500|500|5|500|500");
     *   Paiva paiva2 = new Paiva(); paiva2.parse("2|12.3.2015|500|500|500|500|5|500|500");
     *   Paiva paiva3 = new Paiva(); paiva3.parse("3|12.3.2015|500|500|500|500|5|500|500");
     *   Paiva paiva4 = new Paiva(); paiva4.parse("4|12.3.2015|500|500|500|500|5|500|500");
     *   Paiva paiva5 = new Paiva(); paiva5.parse("5|12.3.2015|500|500|500|500|5|500|500");
     *   paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3); paivat.lisaa(paiva4); paivat.lisaa(paiva5);
     *   List<Paiva> loytyneet; 
     *   loytyneet = (List<Paiva>)paivat.etsi("*12*",1); 
     *   loytyneet.size() === 5; 
     *   loytyneet.get(0) == paiva3 === false; 
     *   loytyneet.get(1) == paiva4 === false; 
     *     
     *   loytyneet = (List<Paiva>)paivat.etsi("*500*",2); 
     *   loytyneet.size() === 5; 
     *   loytyneet.get(0) == paiva3 === false; 
     *   loytyneet.get(1) == paiva5 === false;
     *     
     *   loytyneet = (List<Paiva>)paivat.etsi(null,-1); 
     *   loytyneet.size() === 5; 
     * </pre>
     */
    public Collection<Paiva> etsi(String hakuehto, int k) { 
        Collection<Paiva> loytyneet = new ArrayList<Paiva>(); 
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
 
        for (Paiva paiva : this) { 
            if (WildChars.onkoSamat(paiva.anna(hk), ehto)) loytyneet.add(paiva);   
        } 
        return loytyneet;
        
    }
    /**
     * Etsii ja korvaa Paivan tiedot jos tunnus numero on sama
     * @param paiva Paiva jota korvataan
     * @throws SailoException jos tulee virhe
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * #import kanta.SailoException;
     * Paivat paivat = new Paivat();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
     * paiva1.rekisteroi(); paiva2.rekisteroi();
     * paivat.getLkm() === 0;
     * paivat.korvaaTaiLisaa(paiva1); paivat.getLkm() === 1;
     * paivat.korvaaTaiLisaa(paiva2); paivat.getLkm() === 2;
     * Paiva paiva3 = paiva1.clone();
     * paiva3.aseta(3,"100");
     * Iterator<Paiva> it = paivat.iterator();
     * it.next() == paiva1 === true;
     * paivat.korvaaTaiLisaa(paiva3); paivat.getLkm() === 2;
     * it = paivat.iterator();
     * Paiva j0 = it.next();
     * j0 === paiva3;
     * j0 == paiva3 === true;
     * j0 == paiva1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Paiva paiva) throws SailoException {
        int id = paiva.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id ) {
                alkiot[i] = paiva;
                muutettu = true;
                return;
            }
        }
        lisaa(paiva);
    }
    /**
     * Poistaa valitun paivan tiedoista
     * @param id Poistettavan tunnus nro
     * @return 1 jos poisto onnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import kanta.SailoException;
     * Paivat paivat = new Paivat();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva(), paiva3 = new Paiva();
     * paiva1.rekisteroi(); paiva2.rekisteroi(); paiva3.rekisteroi();
     * int id1 = paiva1.getTunnusNro();
     * paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3);
     * paivat.poista(id1+1) === 1;
     * paivat.annaId(id1+1) === null; paivat.getLkm() === 2;
     * paivat.poista(id1) === 1; paivat.getLkm() === 1;
     * paivat.poista(id1+3) === 0; paivat.getLkm() === 1;
     * </pre>
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 
    /**
     * Antaa p‰iv‰n ID luvun
     * @param id p‰iv‰ joka halutaan selvitt‰‰
     * @return null jos ep‰onnistuu
     * <pre name="test">
     * #THROWS SailoException
     * #import kanta.SailoException; 
     * Paivat paivat = new Paivat();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva(), paiva3 = new Paiva();
     * paiva1.rekisteroi(); paiva2.rekisteroi(); paiva3.rekisteroi();
     * int id1 = paiva1.getTunnusNro();
     * paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3);
     * paivat.annaId(id1  ) == paiva1 === true;
     * paivat.annaId(id1+1) == paiva2 === true;
     * paivat.annaId(id1+2) == paiva3 === true;
     * </pre>
     */
    public Paiva annaId(int id) { 
        for (Paiva paiva : this) { 
            if (id == paiva.getTunnusNro()) return paiva; 
        } 
        return null; 
    } 
    /**
     * Etsii P‰iv‰n id:n perusteella
     * @param id tunnusnumero, jonka mukaan etsit‰‰n
     * @return lˆytyneen p‰iv‰n indeksi tai -1 jos ei lˆydy
     * <pre name="test">
     * #THROWS SailoException 
     * #import kanta.SailoException;
     * Paivat paivat = new Paivat();
     * Paiva paiva1 = new Paiva(), paiva2 = new Paiva(), paiva3 = new Paiva();
     * paiva1.rekisteroi(); paiva2.rekisteroi(); paiva3.rekisteroi();
     * int id1 = paiva1.getTunnusNro();
     * paivat.lisaa(paiva1); paivat.lisaa(paiva2); paivat.lisaa(paiva3);
     * paivat.etsiId(id1+1) === 1;
     * paivat.etsiId(id1+2) === 2;
     * </pre>
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    } 


    /**
     * Testiohjelma p‰iville
     * @param args ei k‰ytˆss‰
     */
    public static void main(String args[]) {
        Paivat paivat = new Paivat();

        Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
        paiva1.rekisteroi();
        paiva1.vastaaPaivan();
        paiva2.rekisteroi();
        paiva2.vastaaPaivan();

        try {
            paivat.lisaa(paiva1);
            paivat.lisaa(paiva2);

            System.out.println("============= P‰iv‰t testi =================");

            for (int i = 0; i < paivat.getLkm(); i++) {
                Paiva paiva = paivat.anna(i);
                System.out.println("P‰iv‰n nro: " + i);
                paiva.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

