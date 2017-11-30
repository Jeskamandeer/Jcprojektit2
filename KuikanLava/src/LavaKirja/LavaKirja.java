package LavaKirja;


import java.io.File;
import java.util.Collection;
import java.util.List;
import kanta.SailoException;

/**
 * 
 * @author Jere
 * @version 21.6.2017
 * Testien alustus
 * @example
 * <pre name="testJAVA">
 *  private LavaKirja lavakirja;
 *  private Paiva paiva1;
 *  private Paiva paiva2;
 *  private Esiintyja esi11;
 *  private Esiintyja esi12;
 *  private Esiintyja esi13;
 *  private Esiintyja esi14;
 *  private Esiintyja esi15;
 * 
 *  @SuppressWarnings("javadoc")
 *  public void alustaLavaKirja() throws SailoException{
 *    lavakirja = new LavaKirja();
 *    paiva1 = new Paiva(); paiva1.vastaaPaivan(); paiva1.rekisteroi();
 *    paiva2 = new Paiva(); paiva2.vastaaPaivan(); paiva2.rekisteroi();
 *    lavakirja.lisaa(paiva1);
 *    lavakirja.lisaa(paiva2);
 *    esi11 = new Esiintyja(); esi11.vastaaEsiintyja("Metallica"); esi11.rekisteroi();
 *    esi12 = new Esiintyja(); esi12.vastaaEsiintyja("Metallica"); esi12.rekisteroi();
 *    esi13 = new Esiintyja(); esi13.vastaaEsiintyja("Metallica"); esi13.rekisteroi();
 *    esi14 = new Esiintyja(); esi14.vastaaEsiintyja("Metallica"); esi14.rekisteroi();
 *    esi15 = new Esiintyja(); esi15.vastaaEsiintyja("Metallica"); esi15.rekisteroi();
 *    try {
 *    lavakirja.lisaa(paiva1, esi11);
 *    lavakirja.lisaa(paiva2, esi12);
 *    lavakirja.lisaa(paiva1, esi13);
 *    lavakirja.lisaa(paiva2, esi14);
 *    lavakirja.lisaa(paiva1, esi15);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
 */
public class LavaKirja  {
    private  Paivat paivat = new Paivat();
    private  Relaatiot relaatiot = new Relaatiot();
    private  Esiintyjat esiintyjat = new Esiintyjat();
    

    /**
     * Poistaa p‰iv‰n ja esiintyjat
     * @param paiva poistttava p‰iv‰
     * @return montako poistettiin
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaLavaKirja();
     *   lavakirja.annaEsiintyjat(1).size() === 0;
     *   lavakirja.poista(paiva1) === 1;
     * </pre>
     */
    public int poista(Paiva paiva) {
        if ( paiva == null ) return 0;
        int ret = paivat.poista(paiva.getTunnusNro()); 
        
        List<Integer> paivanesiintyjat = relaatiot.hae(paiva.getTunnusNro());
        for (Integer poistettavat : paivanesiintyjat)
           esiintyjat.poistaPaivanEsiintyjat(poistettavat);
        
        List<Integer> paivanrelaatiot = relaatiot.hae(paiva.getTunnusNro());
        for(Integer poistettavarelaatio : paivanrelaatiot)           
            relaatiot.poistaRelaatiot(poistettavarelaatio);
        
        return ret; 
    }
    /**
     * Poistaa esiintyjan p‰iv‰st‰
     * @param esi poistettav esiintyja
     * @example
     * <pre name="test">
     * #THROWS Exception
     *   alustaLavaKirja();
     *   lavakirja.getEsiintyjia() === 5;
     *   lavakirja.poistaEsiintyja(esi12);
     *   lavakirja.getEsiintyjia() === 4;
     */ 
    public void poistaEsiintyja(Esiintyja esi) { 
        relaatiot.poistaRelaatiot(esi.getTunnusNro());
        esiintyjat.poista(esi); 
        
    } 
    /**
     * Hakee Esiintyjien lukum‰‰r‰n
     * @return Esiintyjien lukum‰‰r‰
     */
    public int getEsiintyjia() {
        return esiintyjat.getLkm();
    }
    /**
     * Hakee Relaatioiden lukum‰‰r‰n
     * @return Relaatioiden lukum‰‰r‰
     */
    public int getRelaatiot() {
        return relaatiot.getLkm();
    }
    /**
     * Hakee p‰ivien lukum‰‰r‰n
     * @return P‰ivien lukum‰‰r‰
     */
    public int getPaivia() {
        return paivat.getLkm();
    }
    /**
     * Lis‰‰ p‰iv‰n p‰iviin
     * @param paiva Joka lis‰t‰‰n
     * @throws SailoException virhe
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import kanta.SailoException;
     *  alustaLavaKirja();
     *  lavakirja.getPaivia() === 2;
     *  lavakirja.lisaa(paiva1);
     *  lavakirja.getPaivia() === 3;
     * </pre>
     */
    public void lisaa(Paiva paiva) throws SailoException {
        paivat.lisaa(paiva);
    }
    /**
     * Lis‰‰ esiintyj‰n
     * @param esi Esiintyj‰ joka lis‰t‰‰n
     * @param paiva Paiva johon lis‰t‰‰n
     * @throws SailoException jos tulee virhe
     */
    public void lisaa(Paiva paiva, Esiintyja esi) throws SailoException {  
        
        esiintyjat.lisaa(esi);
        relaatiot.lisaa(paiva.getTunnusNro(), esi.getTunnusNro());
    }
    /**
     * Antaa p‰iv‰n tunnus numeron
     * @param i Mik‰ p‰iv‰ halutaan
     * @return P‰iv‰n tunnus numeron
     * @throws IndexOutOfBoundsException Jos tulee virhe
     */
    public Paiva annaPaiva(int i) throws IndexOutOfBoundsException {
        return paivat.anna(i);
    }
    
    /**
     * Palauttaa "taulukossa" hakuehtoon vastaavien p‰ivien viitteet
     * @param hakuehto hakuehto 
     * @param k etsitt‰v‰n kent‰n indeksi 
     * @return tietorakenteen lˆytyneist‰ p‰ivist‰
     * @throws SailoException Jos jotakin menee v‰‰rin
     * @example
     * <pre name="test">
     *   #THROWS CloneNotSupportedException, SailoException
     *   #import java.util.*;
     *   alustaLavaKirja();
     *   Paiva paiva3 = new Paiva(); paiva3.rekisteroi();
     *   paiva3.aseta(1,"11.3.1996");
     *   lavakirja.lisaa(paiva3);
     *   Collection<Paiva> loytyneet = lavakirja.etsi("*11*",1);
     *   loytyneet.size() === 1;
     *   Iterator<Paiva> it = loytyneet.iterator();
     *   it.next() == paiva3 === true;
     * </pre>
     */ 
    public Collection<Paiva> etsi(String hakuehto, int k) throws SailoException { 
        return paivat.etsi(hakuehto, k); 
    }
    /**
     * Hakee esiintyj‰t
     * @param tunnus Jonka esiintyj‰t halutaan
     * @return Esiintyj‰n ID
     */
    public List<Esiintyja> annaEsiintyjat(int tunnus)  {
        return esiintyjat.annaEsiintyjat(tunnus);
         
    }
    /**
     * Hakee p‰iv‰n relaatiot
     * @param paiva jonka relaatiot haetaan
     * @return Lista relaatioista
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     *
     *  alustaLavaKirja();
     *  Paiva paiva3 = new Paiva();
     *  paiva3.rekisteroi();
     *  lavakirja.lisaa(paiva3);
     * 
     *  List<Integer> loytyneet;
     *  loytyneet = lavakirja.hae(paiva3.getTunnusNro());
     *  loytyneet.size() === 0;
     *  loytyneet = lavakirja.hae(paiva1.getTunnusNro());
     *  loytyneet.size() === 3;
     *  loytyneet = lavakirja.hae(paiva2.getTunnusNro());
     *  loytyneet.size() === 2;
     * </pre>
     */
    public List<Integer> hae(int paiva){
       return relaatiot.hae(paiva);
    }
    /**
     * Lukee tiedot tiedostosta
     * @param nimi Tiedosto josta luetaan
     * @throws SailoException jos tulee virhe
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        paivat = new Paivat(); 
        esiintyjat = new Esiintyjat();
        relaatiot = new Relaatiot();
        setTiedosto(nimi);
        paivat.lueTiedostosta();
        esiintyjat.lueTiedostosta();
        relaatiot.lueTiedostosta();
    }
    
    /**
     * Korvaa paivan tietorakenteessa.  Ottaa paivan omistukseensa.
     * Etsit‰‰n samalla tunnusnumerolla oleva paiva.  Jos ei lˆydy,
     * niin lis‰t‰‰n uutena paivana.
     * @param paiva lis‰t‰‰v‰n p‰iv‰n viite.  Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo t‰ynn‰
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import kanta.SailoException;
     *  alustaLavaKirja();
     *  lavakirja.getPaivia() === 2;
     *  lavakirja.korvaaTaiLisaa(paiva1);
     *  lavakirja.getPaivia() === 2;
     * </pre>
     */ 
    public void korvaaTaiLisaa(Paiva paiva) throws SailoException { 
        paivat.korvaaTaiLisaa(paiva); 
    } 

    /**
     * Tallentaa tiedot
     * @throws SailoException jos tulee virhe
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            relaatiot.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        try {
            esiintyjat.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        try {
            paivat.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    /**
     * Asettaa tiedoston lukemista varten
     * @param nimi tiedston nimi joka luetaan
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        paivat.setTiedostonPerusNimi(hakemistonNimi + "paivat");
        esiintyjat.setTiedostonPerusNimi(hakemistonNimi + "esiintyjat");
        relaatiot.setTiedostonPerusNimi(hakemistonNimi + "relaatiot");
    }
    /**
     * Testiohjlema
     * @param args ei k‰ytˆss‰
     */
    public static void main(String args[]) {
        LavaKirja lavakirja = new LavaKirja();
        try {
            
            Paiva paiva1 = new Paiva(), paiva2 = new Paiva();
            paiva1.rekisteroi();
            paiva1.vastaaPaivan();
            paiva2.rekisteroi();
            paiva2.vastaaPaivan();
            lavakirja.lisaa(paiva1);
            lavakirja.lisaa(paiva2);
            
            Esiintyja esi11 = new Esiintyja(); esi11.vastaaEsiintyja("Metallica"); lavakirja.lisaa(paiva1, esi11);          
            Esiintyja esi12 = new Esiintyja(); esi12.vastaaEsiintyja("Yˆ"); lavakirja.lisaa(paiva2, esi12);   
            Esiintyja esi21 = new Esiintyja(); esi21.vastaaEsiintyja("Neljan Suora"); lavakirja.lisaa(paiva2, esi21);
            Esiintyja esi43 = new Esiintyja(); esi43.vastaaEsiintyja("Matti"); lavakirja.lisaa(paiva1, esi43);           
            Esiintyja esi44 = new Esiintyja(); esi44.vastaaEsiintyja("Teppo"); lavakirja.lisaa(paiva1, esi44);            
            Esiintyja esi45 = new Esiintyja(); esi45.vastaaEsiintyja("Teppo"); lavakirja.lisaa(paiva2, esi45);
      
            System.out.println(lavakirja.getPaivia());
            System.out.println("============= Kerhon testi =================");
            Collection<Paiva> paivat = lavakirja.etsi("", -1);
            int i = 0;
            for (Paiva paiva: paivat) {            
                System.out.println("P‰iv‰ paikassa: " + i);
                paiva.tulosta(System.out);
                
                List<Integer> loytyneet = lavakirja.hae(paiva.getTunnusNro());
                for (int d = 0; d < loytyneet.size(); d++){
                  
                    List<Esiintyja> esiintyjat = lavakirja.annaEsiintyjat(loytyneet.get(d));
                    for( Esiintyja loytyi : esiintyjat)
                        loytyi.tulosta(System.out);            
                }
                i++;
            }
            lavakirja.tallenna();
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }


    
}