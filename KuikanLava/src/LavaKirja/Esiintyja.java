package LavaKirja;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * 
 * @author Jere
 * @version 21.6.2017
 *
 */
public class Esiintyja implements Cloneable  {
    
    private int tunnusNro;
    private String nimi;

   
    private static int seuraavaNro = 1;
    /**
     * Alustetaan esiintyja.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Esiintyja() {
        // Vielä ei tarvita mitään
    }
    /**
     * Alustetaan esiintyja. 
     * @param nimi esiintyjän nimi
     */
    public Esiintyja(String nimi) {
        this.nimi = nimi;
    }
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Esiityjälle.
     * @param nimi1 Esiintyjän nimi
     */
    public void vastaaEsiintyja(String nimi1) {
        this.nimi = nimi1;
        rekisteroi();
            
    }
    /**
     * Tulostetaan esiintyjan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Esiintyjän nimi: " + nimi + " TunnusNro: " + tunnusNro);
    }
    /**
     * Tulostetaan esiintyjan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    /**
     * Antaa esiintyjälle seuraavan rekisterinumeron.
     * @return esiintyjän uusi tunnus_nro
     * @example
     * <pre name="test">
     *   Esiintyja esi1 = new Esiintyja();
     *   esi1.getTunnusNro() === 0;
     *   esi1.rekisteroi();
     *   Esiintyja esi2 = new Esiintyja();
     *   esi2.rekisteroi();
     *   int n1 = esi1.getTunnusNro();
     *   int n2 = esi2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    /**
     * Palautetaan esiintyjän oma id
     * @return esiintyjän id
     * @example
     * <pre name="test">
     *   Esiintyja esi1 = new Esiintyja();
     *   esi1.vastaaEsiintyja("Metallica");
     *   esi1.getTunnusNro() === 5;
     * </pre>
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    /**
     * Palautetaan mille esiintyjän nimi
     * @return esiintyjän nimi
     * @example
     * <pre name="test">
     *   Esiintyja esi1 = new Esiintyja();
     *   esi1.vastaaEsiintyja("Metallica");
     *   esi1.getNimi() === "Metallica";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    /**
     * Asettaa tunnus numeron
     * @param nr asetettava numero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
    /**
     * @return Esiintyjan kenttien lukumäärä
     */
    public int getKenttia() {
        return 2;
    }
    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
   
    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    public String getKysymys(int k) {
        switch (k) {
        	case 0:
            return "Tunnus Numero";
            case 1:
                return "Esiintyjan nimi";

            default:
                return "";
        }
    }
    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     */
    public String anna(int k) {
        switch (k) {
        
        	case 0:
            return "" + tunnusNro;
            
            case 1:
                return "" + nimi;

            default:
                return "???";
        }
    }
    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     */
    public String aseta(int k, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '$', getTunnusNro()));
                return null;
            case 1:
                nimi = Mjonot.erota(sb, '$', nimi);
                return null;
            default:
                return "Väärä kentän indeksi";
        }
    }
    /**
     * Tehdään identtinen klooni jäsenestä
     * @return Object kloonattu jäsen
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *   Esiintyja esi = new Esiintyja();
     *   esi.parse("   2   | Metallica ");
     *   Esiintyja kopio = esi.clone();
     *   kopio.toString() === esi.toString();
     *   esi.parse("   1   |  Metallica ");
     *   kopio.toString().equals(esi.toString()) === false;
     * </pre>
     */
    @Override
    public Esiintyja clone() throws CloneNotSupportedException { 
        return (Esiintyja)super.clone();
    }
    /**
     * Hakee tiedot tiedostosta
     * @param rivi luettava rivi
     * @example
     * <pre name="test">
     *  Esiintyja esi = new Esiintyja();
     *   esi.parse("   2   |  Metallica ");
     *   esi.toString()    === "2|Metallica";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }
    
    @Override
    /**
    * Palauttaa Esiintyjan tiedot merkkijonona jonka voi tallentaa tiedostoon.
    * @return esiintyjä tolppaeroteltuna merkkijonona 
    * @example
    * <pre name="test">
    *   Esiintyja esi = new Esiintyja();
    *   esi.parse("   2   |Metallica");
    *   esi.toString()    === "2|Metallica";
    * </pre>
    */
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
   
    @Override
    public int hashCode() {
        return tunnusNro;
    }
    /**
     * Testiohjelma Esiintyjälle.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Esiintyja esi1 = new Esiintyja();
        Esiintyja esi2 = new Esiintyja();
        esi1.vastaaEsiintyja("metallica");
        esi1.tulosta(System.out);
        esi2.vastaaEsiintyja("Yö");
        esi2.tulosta(System.out);
    }

}