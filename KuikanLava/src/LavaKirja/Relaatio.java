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
public class Relaatio {
    private int tunnusNro;
    private int pId;
    private int eId;
   
    private static int seuraavaNro = 1;
    /**
     * Alustetaan relaatio.  Toistaiseksi ei tarvitse tehdä mitään
     */
    public Relaatio() {
        // Vielä ei tarvita mitään
    }
    /**
     * Alustetaan relaatio
     * @param pId Päivän ID
     * @param eId Esiintyjän ID
     */
    public Relaatio(int pId, int eId) {
        this.pId = pId;
        this.eId = eId;
        
    }
   /**
    * Saadaan täytettyä testiarvot relaatiolle
    * @param pId1 Päivän ID
    * @param eId1 Esiintyjän ID
    */
    public void vastaaRelaatio(int pId1, int eId1) {
        this.pId = pId1;
        this.eId = eId1;
        rekisteroi();
        
    }
    /**
     * Tulostetaan relaation tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println( "Päivän Id " + pId + " Esiintyjän Id: " + eId + "TunnusNro " + tunnusNro );
    }
    /**
     * Palauttaa relaation string muodossa
     * @example
     * <pre name="test">
     *   Relaatio rela = new Relaatio(1,1);
     *   rela.parse("   1  |  1   |1 ");
     *   rela.toString().startsWith("1|1|1") === true; 
     * </pre> 
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + pId + "|" + eId;
    }
    /**
     * Tulostetaan relaation tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    /**
     * Asettaa tunnusnumeron
     * @param nr asetettava numero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
    }
    /**
     * Antaa relaatiolle seuraavan rekisterinumeron.
     * @return relaation uusi tunnus_nro
     * @example
     * <pre name="test">
     * Relaatio rela1 = new Relaatio();
     * rela1.getTunnusNro() === 0;
     * rela1.rekisteroi();
     * Relaatio rela2 = new Relaatio();
     * rela2.rekisteroi();
     * int n1 = rela1.getTunnusNro();
     * int n2 = rela2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    /**
     * Palautetaan relaation oma id
     * @return relaation id
    * @example
    * <pre name="test">
    * Relaatio rela = new Relaatio();
    * rela.rekisteroi();
    * rela.getTunnusNro() === 3;
    * </pre>
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    /**
     * Palautetaan Päivä Id relaatiosta
     * @return Päivän id
     * @example
    * <pre name="test">
    * Relaatio rela = new Relaatio(1, 1);
    * rela.getpId() === 1;
    * </pre>
     */
    public int getpId() {
        return pId;
    }
    /**
     * Palautetaan Esiintyjän ID relaatiossa
     * @return Esiintyjän ID
     * @example
    * <pre name="test">
    * Relaatio rela = new Relaatio(1, 1);
    * rela.geteId() === 1;
    * </pre>
     */
    public int geteId() {
        return eId;
    }
    /**
     * Irrottaa tiedotriviltä
     * @param rivi joka luetaan
     * @example
     * <pre name="test">
     *   Relaatio rela = new Relaatio();
     *   rela.parse("0|1|1 ");
     *   rela.toString().startsWith("0|1|1") === true; 
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        pId = Mjonot.erota(sb, '|', pId);
        eId = Mjonot.erota(sb, '|', eId);
 
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
     * Testiohjelma relaatioille.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Relaatio rela1 = new Relaatio();
        Relaatio rela2 = new Relaatio();
        rela1.vastaaRelaatio(1 , 1);
        rela1.tulosta(System.out);
        rela2.vastaaRelaatio(1, 2);
        rela2.tulosta(System.out);
    }
}