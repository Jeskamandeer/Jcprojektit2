package LavaKirja;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import kanta.SailoException;
/**
 * 
 * @author Jere
 * @version 21.6.2017
 *
 */
public class Relaatiot implements Iterable<Relaatio> {
    @SuppressWarnings("unused")
    private boolean         muutettu = false;
    private String           tiedostonPerusNimi  = "Relaatiot";
   
  
    /** Taulukko relaatioista */
    
    private final Collection<Relaatio> alkiot        = new ArrayList<Relaatio>();
    /**
     * Relaatioiden alustaminen
     */
    public Relaatiot() {
        // toistaiseksi ei tarvitse tehd‰ mit‰‰n
    }
    
    /**
     * Lis‰‰ relaation tietorakenteeseen
     * @param pId1 P‰iv‰n tunnusNro
     * @param eId1 Esiintyj‰n tunnusnro
     */
    public void lisaa(int pId1, int eId1) {
        Relaatio rela = new Relaatio();
        rela.vastaaRelaatio(pId1, eId1);      
        alkiot.add(rela);
    }
	    /**
	     * Lukee tiedot tiedostosta
	     * @param tied Tiedosto josta luetaan
	     * @throws SailoException Jos tulee virhe
 	     * @example
	     * <pre name="test">
	     * #THROWS SailoException
	     * #import java.io.File;
	     * #import java.util.List;
	     * #import java.util.Iterator;
	     * #import kanta.SailoException;
	     *  Relaatiot relaatiot = new Relaatiot();
	     *  String tiedNimi = "testiRelaatio";
	     *  File ftied = new File(tiedNimi+".dat");
	     *  ftied.delete();
	     *  relaatiot.lueTiedostosta(tiedNimi); #THROWS SailoException
	     *  relaatiot.lisaa(1, 1);
	     *  relaatiot.lisaa(1, 2);
	     *  relaatiot.lisaa(1, 3);
	     *  relaatiot.lisaa(1, 4);
	     *  relaatiot.lisaa(1, 5);
	     *  relaatiot.tallenna();
	     *  relaatiot = new Relaatiot();
	     *  relaatiot.lueTiedostosta(tiedNimi);
	     *  Iterator<Relaatio> i = relaatiot.iterator();
	     *  i.hasNext() === true;
	     *  relaatiot.lisaa(1, 6);
	     *  relaatiot.tallenna();
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
                Relaatio rel = new Relaatio();
                rel.parse(rivi); 
                lisaa(rel.getpId(), rel.geteId());
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }
   
    /**
     * Luetaan aikaisemmin annetun nimisest‰ tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
   
   
    /**
     * Tallentaa Relaatiot tiedostoon.
     * @throws SailoException jos talletus ep‰onnistuu
     */
    public void tallenna() throws SailoException {
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); 
        ftied.renameTo(fbak); 
        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Relaatio rel : this) {
                fo.println(rel.toString());
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
     * @param tied nimi jota k‰ytet‰‰n
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    /**
     * Palauttaa tiedoston nimen, jota k‰ytet‰‰n tallennukseen
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
     * Palauttaa Relaatioiden  lukum‰‰r‰n
     * @return Relaatioiden  lukum‰‰r‰
     */
    public int getLkm() {
        return alkiot.size();
    }
    /**
     * Iteraattori kaikkien relaatioiden l‰pik‰ymiseen
     * @return Relaatioiteraattori
 	 */
    @Override
    public Iterator<Relaatio> iterator() {
        return alkiot.iterator();
    }
    /**
     * 
     * @param tunnusnro jonka avulla haetaan relaatioita
     * @return Kaikki lˆydetyt relaatiot
     * @example
     * <pre name="test">
     * #import java.util.*;
     *
     *  Relaatiot relaatiot = new Relaatiot();
     *  relaatiot.lisaa(1, 1);
	 *  relaatiot.lisaa(1, 2);
	 *  relaatiot.lisaa(1, 3);
	 *  relaatiot.lisaa(1, 4);
	 *  relaatiot.lisaa(1, 5);
     * 
     *  List<Relaatio> loytyneet;
     *  loytyneet = relaatiot.annaRelaatio(1);
     *  loytyneet.size() === 1;
     *  loytyneet = relaatiot.annaRelaatio(2);
     *  loytyneet.size() === 1;
     *  loytyneet = relaatiot.annaRelaatio(5);
     *  loytyneet.size() === 1;
     * </pre>
     */
    public List<Relaatio> annaRelaatio(int tunnusnro) {
        List<Relaatio> loydetyt = new ArrayList<Relaatio>();
        for (Relaatio rela : alkiot)
            if (rela.getTunnusNro() == tunnusnro) loydetyt.add(rela);
        return loydetyt;
    }
    /**
     * Hakee Relaatiot jossa esintyy p‰iv‰n ID
     * @param paiva joka haetaan
     * @return lista esiintyjein tunnusnumeroista
     */
    public List<Integer> hae(int paiva){
        
            List<Integer> loydetyt = new ArrayList<Integer>();
            for (Relaatio rela : alkiot)
                if (rela.getpId() == paiva) loydetyt.add(rela.geteId());
            return loydetyt;
            
            
        }
    /**
     * Poistaa relaation
     * @param eId relaation esiintyjan tunnusnumero
     * @return montako poistettiin
     */
    public int poistaRelaatiot(int eId) {
        int n = 0;
        for (Iterator<Relaatio> it = alkiot.iterator(); it.hasNext();) {
            Relaatio rela = it.next();
            if ( rela.geteId() == eId ) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * Testiohjelma Relaatioille
     * @param args ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Relaatiot relaatiot = new Relaatiot();
        
        relaatiot.lisaa(1, 2);
        relaatiot.lisaa(1, 1);
        relaatiot.lisaa(1, 2);
        relaatiot.lisaa(1, 4);
        relaatiot.lisaa(2, 1);
        
        System.out.println("============= Relaatiot testi =================");
        for (int i = 1; i < relaatiot.getLkm() + 1; i++) {
        List<Relaatio> relaatiot2 = relaatiot.annaRelaatio(i);
        for (Relaatio rela : relaatiot2) {
            System.out.print(rela.getTunnusNro() + " ");
            rela.tulosta(System.out);
         
        }
        }
    }


}

