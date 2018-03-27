package LavaKirja;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import kanta.SailoException;
/**
 * 
 * @author Jere
 * @version 21.6.2017
 *
 */
public class Esiintyjat implements Iterable<Esiintyja> { 
        private String tiedostonPerusNimi = "Esiintyjat";
        private boolean muutettu = false; 
        
        /** Taulukko esiintyjista */
        
        private final Collection<Esiintyja> alkiot        = new ArrayList<Esiintyja>();
        /**
         * Esiintyjien alustaminen
         */
        public Esiintyjat() {
            // toistaiseksi ei tarvitse tehdä mitään
        }
        /**
         * Lisää uuden esiintyjan tietorakenteeseen.  Ottaa esiintyjan omistukseensa.
         * @param esi lisättävä esiintyjä.  Huom tietorakenne muuttuu omistajaksi
         */
        public void lisaa(Esiintyja esi) {
            alkiot.add(esi);
            muutettu = true; 
        }
        /**
         * Lukee tiedostosta esiintyjat ja lisää ne
         * @param tied josta luetaan
         * @throws SailoException jos tulee virhe
	     * @example
	     * <pre name="test">
	     * #THROWS SailoException
	     * #import java.io.File;
	     * #import java.util.List;
	     * #import java.util.Iterator;
	     * #import kanta.SailoException;
	     *  Esiintyjat esiintyjat = new Esiintyjat();
	     *  Esiintyja esi11  = new Esiintyja(); esi11.vastaaEsiintyja("Metallica");
	     *  Esiintyja esi12  = new Esiintyja(); esi12.vastaaEsiintyja("KoiraBänd");
	     *  Esiintyja esi13  = new Esiintyja(); esi13.vastaaEsiintyja("KissaBönd");
	     *  Esiintyja esi14  = new Esiintyja(); esi14.vastaaEsiintyja("koira");
	     *  Esiintyja esi15  = new Esiintyja(); esi15.vastaaEsiintyja("2");
	     *  String tiedNimi = "testiKirja";
	     *  File ftied = new File(tiedNimi+".dat");
	     *  ftied.delete();
	     *  esiintyjat.lueTiedostosta(tiedNimi); #THROWS SailoException
	     *  esiintyjat.lisaa(esi11);
	     *  esiintyjat.lisaa(esi12);
	     *  esiintyjat.lisaa(esi13);
	     *  esiintyjat.lisaa(esi14);
	     *  esiintyjat.lisaa(esi15);
	     *  esiintyjat.tallenna();
	     *  esiintyjat = new Esiintyjat();
	     *  esiintyjat.lueTiedostosta(tiedNimi);
	     *  Iterator<Esiintyja> i = esiintyjat.iterator();
	     *  i.next().toString() === esi11.toString();
	     *  i.next().toString() === esi12.toString();
	     *  i.next().toString() === esi13.toString();
	     *  i.next().toString() === esi14.toString();
	     *  i.next().toString() === esi15.toString();
	     *  i.hasNext() === false;
	     *  esiintyjat.lisaa(esi13);
	     *  esiintyjat.tallenna();
	     *  ftied.delete() === true;
	     *  File fbak = new File(tiedNimi+".bak");
	     *  fbak.delete() === true;
	     * </pre>
         */
        public void lueTiedostosta(String tied) throws SailoException {
            setTiedostonPerusNimi(tied);
            try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
                String rivi;
                while ( (rivi = fi.readLine()) != null ) {
                    rivi = rivi.trim();
                    if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                    Esiintyja esi = new Esiintyja();
                    esi.parse(rivi); 
                    lisaa(esi);
                }
                muutettu = false;
            } catch ( FileNotFoundException e ) {
                throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
            } catch ( IOException e ) {
                throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
            }
        }
       
        /**
         * Luetaan aikaisemmin annetun nimisestä tiedostosta
         * @throws SailoException jos tulee poikkeus
         */
        public void lueTiedostosta() throws SailoException {
            lueTiedostosta(getTiedostonPerusNimi());
        }  
        /**
         * Tallentaa Esiintyjät tiedostoon.
         * @throws SailoException jos talletus epäonnistuu
         */
        public void tallenna() throws SailoException {
            if ( !muutettu ) return;
            File fbak = new File(getBakNimi());
            File ftied = new File(getTiedostonNimi());
            fbak.delete(); 
            ftied.renameTo(fbak); 
            try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
                for (Esiintyja esi : this) {
                    fo.println(esi.toString());
                }
            } catch ( FileNotFoundException ex ) {
                throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
            } catch ( IOException ex ) {
                throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
            }
            muutettu = false;
        }
        /**
         * Asettaa tiedostolle perusnimen
         * @param tied nimi jota hakutaan käyttää
         */
        public void setTiedostonPerusNimi(String tied) {
            tiedostonPerusNimi = tied;
        }
        /**
         * Palauttaa tiedoston nimen, jota käytetään tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonPerusNimi() {
            return tiedostonPerusNimi;
        }
        /**
         * Palauttaa tiedoston nimen, jota käytetään tallennukseen
         * @return tallennustiedoston nimi
         */
        public String getTiedostonNimi() {
            return tiedostonPerusNimi + ".dat";
        }
        /**
         * Palauttaa varakopiotiedoston nimen
         * @return varakopiotiedoston nimi
         */
        public String getBakNimi() {
            return tiedostonPerusNimi + ".bak";
        }
        /**
         * Palauttaa Esiintyjien  lukumäärän
         * @return Esiintyjien lukumäärä
         */
        public int getLkm() {
            return alkiot.size();
        }
        /**
         * Iteraattori kaikkien esiintyjien läpikäymiseen
         * @return esiintyjäiteraattori
         *
         */
        @Override
        public Iterator<Esiintyja> iterator() {
            return alkiot.iterator();
        }
        /**
         * 
         * @param tunnusnro jonka avulla haetaan esiintyjiä
         * @return Kaikki löydetyt esiintyjät
         */
        public List<Esiintyja> annaEsiintyjat(int tunnusnro) {
            List<Esiintyja> loydetyt = new ArrayList<Esiintyja>();
            for (Esiintyja esi : alkiot)
                if (esi.getTunnusNro() == tunnusnro) loydetyt.add(esi);
            return loydetyt;
        }
 
        /**
         * Poistaa esiintyjan
         * @param esi poistettava esiintyjä
         * @return poistettu määrä
         * @example
         * <pre name="test">
         * #THROWS SailoException
         * #import java.io.File;
         * #import java.util.List;
         * #import kanta.SailoException;
	     *  Esiintyjat esiintyjat = new Esiintyjat();
	     *  Esiintyja esi11  = new Esiintyja(); esi11.vastaaEsiintyja("Metallica");
	     *  Esiintyja esi12  = new Esiintyja(); esi12.vastaaEsiintyja("KoiraBänd");
	     *  Esiintyja esi13  = new Esiintyja(); esi13.vastaaEsiintyja("KissaBönd");
	     *  Esiintyja esi14  = new Esiintyja(); esi14.vastaaEsiintyja("koira");
	     *  Esiintyja esi15  = new Esiintyja(); esi15.vastaaEsiintyja("2");
	     *  esiintyjat.lisaa(esi11);
	     *  esiintyjat.lisaa(esi12);
	     *  esiintyjat.lisaa(esi13);
	     *  esiintyjat.lisaa(esi14);
	     *  esiintyjat.poista(esi15) === false ; esiintyjat.getLkm() === 4;
	     *  esiintyjat.poista(esi14) === true;   esiintyjat.getLkm() === 3;
	     *  List<Esiintyja> h = esiintyjat.annaEsiintyjat(1);
	     *  h.size() === 1;
	     *  h.get(0) === esi11;
	     * </pre>
         */
        public boolean poista(Esiintyja esi) {
            boolean ret = alkiot.remove(esi);
            if (ret) muutettu = true;
            return ret;
        }
       
        /**   
         * Laitetaan muutos, jolloin pakotetaan tallentamaan.     
         */   
        public void setMuutos() {   
            muutettu = true;   
        } 
       
        /**
         * Poistaa kaikki tietyn tietyn jäsenen harrastukset
         * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
         * @return montako poistettiin
	     * @example
	     * <pre name="test">
	     * #import java.util.List;
	     *  Esiintyjat esiintyjat = new Esiintyjat();
	     *  Esiintyja esi11  = new Esiintyja(); esi11.vastaaEsiintyja("Metallica");
	     *  Esiintyja esi12  = new Esiintyja(); esi12.vastaaEsiintyja("KoiraBänd");
	     *  Esiintyja esi13  = new Esiintyja(); esi13.vastaaEsiintyja("KissaBönd");
	     *  Esiintyja esi14  = new Esiintyja(); esi14.vastaaEsiintyja("koira");
	     *  Esiintyja esi15  = new Esiintyja(); esi15.vastaaEsiintyja("2");
	     *  esiintyjat.lisaa(esi11);
	     *  esiintyjat.lisaa(esi12);
	     *  esiintyjat.lisaa(esi13);
	     *  esiintyjat.lisaa(esi14);
	     *  esiintyjat.lisaa(esi14);
	     *  esiintyjat.poistaPaivanEsiintyjat(2) === 0;  esiintyjat.getLkm() === 5;
	     *  esiintyjat.poistaPaivanEsiintyjat(3) === 0;  esiintyjat.getLkm() === 5;
	     * </pre>
         */
        public int poistaPaivanEsiintyjat(int tunnusNro) {
            int n = 0;
            for (Iterator<Esiintyja> it = alkiot.iterator(); it.hasNext();) {
                Esiintyja esi = it.next();
                if ( esi.getTunnusNro() == tunnusNro ) {
                    it.remove();
                    n++;
                }
            }
            if (n > 0) muutettu = true;
            return n;
        }
        
       
        
        
        
        
        /**
         * Testiohjelma Esiintyjille
         * @param args ei käytössä
         */
        public static void main(String[] args) {
            Esiintyjat esiintyjat = new Esiintyjat();
            Esiintyja esi1 = new Esiintyja();
            esi1.vastaaEsiintyja("Metallica");
            Esiintyja esi2 = new Esiintyja();
            esi2.vastaaEsiintyja("Yö");
            Esiintyja esi3 = new Esiintyja();
            esi3.vastaaEsiintyja("Neljan Suora");
            Esiintyja esi4 = new Esiintyja();
            esi4.vastaaEsiintyja("Matti");
            esiintyjat.lisaa(esi1);
            esiintyjat.lisaa(esi2);
            esiintyjat.lisaa(esi3);
            esiintyjat.lisaa(esi4);
            esiintyjat.lisaa(esi1);
            System.out.println("============= Esiintyjät testi =================");
            for (int i = 0; i < esiintyjat.getLkm(); i++){
            List<Esiintyja> esiintyjat2 = esiintyjat.annaEsiintyjat(i);
            for (Esiintyja har : esiintyjat2) {
                System.out.print(har.getTunnusNro() + " ");
                har.tulosta(System.out);
               
            }
            
            }
    }
        
}

