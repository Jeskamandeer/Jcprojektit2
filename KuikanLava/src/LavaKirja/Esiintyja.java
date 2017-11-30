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
     * Alustetaan esiintyja.  Toistaiseksi ei tarvitse tehd� mit��n
     */
    public Esiintyja() {
        // Viel� ei tarvita mit��n
    }
    /**
     * Alustetaan esiintyja. 
     * @param nimi esiintyj�n nimi
     */
    public Esiintyja(String nimi) {
        this.nimi = nimi;
    }
    /**
     * Apumetodi, jolla saadaan t�ytetty� testiarvot Esiityj�lle.
     * @param nimi1 Esiintyj�n nimi
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
        out.println("Esiintyj�n nimi: " + nimi + " TunnusNro: " + tunnusNro);
    }
    /**
     * Tulostetaan esiintyjan tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    /**
     * Antaa esiintyj�lle seuraavan rekisterinumeron.
     * @return esiintyj�n uusi tunnus_nro
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
     * Palautetaan esiintyj�n oma id
     * @return esiintyj�n id
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
     * Palautetaan mille esiintyj�n nimi
     * @return esiintyj�n nimi
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
     * @return Esiintyjan kenttien lukum��r�
     */
    public int getKenttia() {
        return 2;
    }
    /**
     * @return ensimm�inen k�ytt�j�n sy�tett�v�n kent�n indeksi
     */
    public int ekaKentta() {
        return 1;
    }
   
    /**
     * @param k mink� kent�n kysymys halutaan
     * @return valitun kent�n kysymysteksti
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
     * @param k Mink� kent�n sis�lt� halutaan
     * @return valitun kent�n sis�lt�
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
     * Asetetaan valitun kent�n sis�lt�.  Mik�li asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k mink� kent�n sis�lt� asetetaan
     * @param s asetettava sis�lt� merkkijonona
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
                return "V��r� kent�n indeksi";
        }
    }
    /**
     * Tehd��n identtinen klooni j�senest�
     * @return Object kloonattu j�sen
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
    * @return esiintyj� tolppaeroteltuna merkkijonona 
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
     * Testiohjelma Esiintyj�lle.
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Esiintyja esi1 = new Esiintyja();
        Esiintyja esi2 = new Esiintyja();
        esi1.vastaaEsiintyja("metallica");
        esi1.tulosta(System.out);
        esi2.vastaaEsiintyja("Y�");
        esi2.tulosta(System.out);
    }

}