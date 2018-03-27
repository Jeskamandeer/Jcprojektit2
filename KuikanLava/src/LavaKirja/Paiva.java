package LavaKirja;

import fi.jyu.mit.ohj2.Mjonot;
import java.util.Random;
import java.io.OutputStream;
import java.io.PrintStream;

import kanta.Pvm;
import kanta.SisaltaaTarkistaja;


/**
 * 
 * @author Jere
 * @version 21.6.2017
 *
 */
public class Paiva implements Cloneable{
    
    private int    		tunnusNro;
    private String          paivamaara   = " ";
    private int          liput           = 0;
    private int          etuliput        = 0;
    private int          baaritulot      = 0;
    private int          kioskitulot     = 0;
    private int          menot           = 0;
    private int        esiintyjahinta    = 0;
    private int        ilmaisliput       = 0;
    private int        pohjakassa        = 0;
    private String 		virhe;
   
    
    private static int seuraavaNro    = 1;
    
    /**
     * Oletusp‰iv‰
     */
    public Paiva(){
    	paivamaara = "10.3.1996";
    }
   /**
    * Luo paivan
    */
    public void vastaaPaivan() {
        paivamaara =  random() + ".10.2016";
        liput = 100;
        etuliput = 150;
        baaritulot = 1500;
        kioskitulot = 1000;
        menot = 2000;
        esiintyjahinta = 500;
        ilmaisliput  = 15;
        pohjakassa = 700;
    }
    /**
     * Luo satunnaisen luvun 1 ja 10 v‰lilt‰
     * @return luodun numeron
     */
    public int random(){  
        Random rand = new Random();
        int  n = rand.nextInt(10) + 1;
        return n;
    }
   /**
    * Tulosta funktio
    * @param out Teksti joka tulostetaan
    */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + "  " + paivamaara);
        out.println("Liput: " + liput + " Etuliput:  " + etuliput + " Ilmaisliput: " + ilmaisliput);
        out.println("Menot: " + menot + " " + "Pohjakassa: " + pohjakassa + " " + "Esiintyj‰n hinta: " +  esiintyjahinta );    
    }
    /**
     * Tulostaa tiedot
     * @param os ei k‰ytˆss‰
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
      
    /**
     * Rekisterˆi tiedot
     * @return 0 ja kasvattaa seuraavaa numeroa
     * @example
     * <pre name="test">
     * Paiva paiva1 = new Paiva();
     * paiva1.getTunnusNro() === 0;
     * paiva1.rekisteroi();
     * Paiva paiva2 = new Paiva();
     * paiva2.rekisteroi();
     * int n1 = paiva1.getTunnusNro();
     * int n2 = paiva2.getTunnusNro();
     * n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        if ( tunnusNro != 0 ) return tunnusNro;
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return 0;
    }
   /**
    * Hakee tunnusnumeron
    * @return tunnusnumero
    * @example
    * <pre name="test">
    * Paiva paiva = new Paiva();
    * paiva.rekisteroi();
    * paiva.getTunnusNro() === 6;
    * </pre>
    */
    public int getTunnusNro() {
        return tunnusNro;
    }
    /**
     * Hakee tunnusNumeron
     * @param nr monesko numero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    /**
     * Hakee p‰iv‰nm‰‰r‰n string muodossa
     * @return P‰iv‰m‰‰r‰ string muodossa
     */
    public String getPaivamaara() {
        return paivamaara;
    }
    
    /**
     * Hakee lippum‰‰r‰n
     * @return Lippum‰‰r‰t
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getLiput() === 100;
     * </pre>
     */
    public int getLiput() {
        return liput;
    }
    /**
     * Hakee etulippujen m‰‰r‰n
     * @return Hakee etuliput
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getEtuLiput() === 150;
     * </pre>
     */
    public int getEtuLiput() {
        return etuliput;
    }
    /**
     * Hakee baaritulot
     * @return Baaritulot
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getBaariTulot() === 1500;
     * </pre>
     */
    public int getBaariTulot() {
        return baaritulot;
    }
    /**
     * Hakee kioskitulot
     * @return Kioskitulot
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getKioskiTulot() === 1000;
     * </pre>
     */
    public int getKioskiTulot() {
        return kioskitulot;
    }
    /**
     * Hakee menojen m‰‰r‰n
     * @return Menojen m‰‰r‰
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getMenot() === 2000;
     * </pre>
     */
    public int getMenot() {
        return menot;
    }
    /**
     * Hakee ilmaisliput
     * @return Ilmaislippujen m‰‰r‰n
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getIlmaisLiput() === 15;
     * </pre>
     */
    public int getIlmaisLiput() {
        return ilmaisliput;
    }
    /**
     * Hakee pohjakassan
     * @return pohjakassan m‰‰r‰
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getPohjaKassa() === 700;
     * </pre>
     */
    public int getPohjaKassa() {
        return pohjakassa;
    }
    /**
     * Hakee esiintyj‰n hinnan
     * @return esiintyj‰n hinta
     * @example
     * <pre name="test">
     * Paiva paiva = new Paiva();
     * paiva.vastaaPaivan();
     * paiva.getEsiintyja() === 500;
     * </pre>
     */
    public int getEsiintyja() {
        return esiintyjahinta;
    }
    /**
     * Muuttaa tiedot tiedostoon talletettavaan muotoon
     * @example
     * <pre name="test">
     *   Paiva paiva = new Paiva();
     *   paiva.parse("   3  |  10.3.1996   | 0|0|0|0|0|0|0");
     *   paiva.toString().startsWith("3|10.3.1996|0|") === true; // on enemm‰kin kuin 3 kentt‰‰, siksi loppu |
     * </pre> 
     */
    @Override
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
    /**
     * Hakee p‰iv‰n tiedot tiedostosta
     * @param rivi rivi josta tiedot haetaan
     * @example
     * <pre name="test">
     *   Paiva paiva = new Paiva();
     *   paiva.parse("0|10.3.1996|0|0|0|0|0|0|0");
     *   paiva.getTunnusNro() === 0;
     *   paiva.toString().startsWith("0|10.3.1996|0|0|0|0|0|0|0") === true; 
     * </pre>
     */
    public void parse(String rivi) {

        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }

    @Override
    public int hashCode() {
        return tunnusNro;
    }
 
    /**
     * Ekakentt‰ josta voidaan kysy‰ tietja
     * @return kent‰n numero
     */
    public int ekaKentta() {       
        return 1;
    }
    /**
     * Montako kentt‰‰ on 
     * @return m‰‰r‰
     */
    public int getKenttia() {   
        return 9;
    }
    /**
     * Antaa k:n kent‰n sis‰llˆn merkkijonona
     * @param k monenenko kent‰n sis‰ltˆ palautetaan
     * @return kent‰n sis‰ltˆ merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + paivamaara;
        case 2: return "" + liput;
        case 3: return "" + etuliput;
        case 4: return "" + baaritulot;
        case 5: return "" + kioskitulot;
        case 6: return "" + menot;
        case 7: return "" + esiintyjahinta;
        case 8: return "" + ilmaisliput;
        case 9: return "" + pohjakassa;
        default: return "";
        }
    }
    /**
     * Palauttaa k:tta p‰iv‰n kentt‰‰ vastaavan kysymyksen
     * @param k kuinka monennen kent‰n kysymys palautetaan (0-alkuinen)
     * @return k:netta kentt‰‰ vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "Paivamaara";
        case 2: return "Liput";
        case 3: return "Etuliput";
        case 4: return "Baaritulot";
        case 5: return "Kioskitulot";
        case 6: return "Menot";
        case 7: return "Esiintyjaninta";
        case 8: return "Ilmaisliput";
        case 9: return "Pohjakassa";
        default: return "";
        }
    }
    /**
     * Asettaa k:n kent‰n arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kent‰n arvo asetetaan
     * @param jono jonoa joka asetetaan kent‰n arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, 'ß', getTunnusNro()));
            return null;
        case 1:
        	Pvm paiva = new Pvm();
            int p = Mjonot.erota(sb, '.', 0);
            int kk = Mjonot.erota(sb, '.', 0);
            int v = Mjonot.erota(sb, ' ', 0);

        	virhe = paiva.tarkista(p, kk, v);
        	if ( virhe != null ) return virhe;
        	
            paivamaara = tjono;
            return null;
        case 2:  	
            SisaltaaTarkistaja liput1 = new SisaltaaTarkistaja("0123456789");
            virhe = liput1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            liput = Integer.parseInt(sb.toString());
            return null;
            
        case 3:
            SisaltaaTarkistaja etuliput1 = new SisaltaaTarkistaja("0123456789");
            virhe = etuliput1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            etuliput = Integer.parseInt(sb.toString());
            return null;
        case 4:
            SisaltaaTarkistaja baaritulot1 = new SisaltaaTarkistaja("0123456789");
            virhe = baaritulot1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            baaritulot = Integer.parseInt(sb.toString());
            return null;
        case 5:
            SisaltaaTarkistaja kioskitulot1 = new SisaltaaTarkistaja("0123456789");
            virhe = kioskitulot1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            kioskitulot = Integer.parseInt(sb.toString());
            return null;
        case 6:
            SisaltaaTarkistaja menot1 = new SisaltaaTarkistaja("0123456789");
            virhe = menot1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            menot = Integer.parseInt(sb.toString());
            return null;
        case 7:
            SisaltaaTarkistaja esiintyja1 = new SisaltaaTarkistaja("0123456789");
            virhe = esiintyja1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            esiintyjahinta = Integer.parseInt(sb.toString());
            return null;
        case 8:
            SisaltaaTarkistaja ilmaisliput1 = new SisaltaaTarkistaja("0123456789");
            virhe = ilmaisliput1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            ilmaisliput = Integer.parseInt(sb.toString());
            return null;
        case 9:
            SisaltaaTarkistaja pohjakassa1 = new SisaltaaTarkistaja("0123456789");
            virhe = pohjakassa1.tarkista(sb.toString());
            if ( virhe != null ) return virhe;
            pohjakassa = Integer.parseInt(sb.toString());
            return null;

        default: return "";
        }
    }
    
    /**
     * Tehd‰‰n identtinen klooni p‰iv‰st‰
     * @return Object kloonattu P‰iv‰
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     *   Paiva paiva = new Paiva();
     *   paiva.parse("  0  |  10.3.1996   |0|0|0|0|0|0|0");
     *   Paiva kopio = paiva.clone();
     *   kopio.toString() === paiva.toString();
     *   paiva.parse("   0  |  10.3.1996   |1|0|0|0|0|0|0");
     *   kopio.toString().equals(paiva.toString()) === false;
     * </pre>
     */   
    @Override
    public Paiva clone() throws CloneNotSupportedException {
        Paiva uusi;
        uusi = (Paiva) super.clone();
        return uusi;
    }
   
   
    /**
     * Tutkii onko P‰iv‰n tiedot samat kuin parametrina tuodun p‰iv‰n tiedot
     * @param paiva Paiva johon verrataan
     * @return true jos kaikki tiedot samat, false muuten
     * @example
     * <pre name="test">
     *   Paiva paiva1 = new Paiva();
     *   paiva1.parse("   0  | 10.3.1996 | 0|0|0|0|0|0|0");
     *   Paiva paiva2 = new Paiva();
     *   paiva2.parse("   0  | 10.3.1996 | 0|0|0|0|0|0|0");
     *   Paiva paiva3 = new Paiva();
     *   paiva3.parse("   0  | 10.3.1996 | 1|0|0|0|0|0|0");
     *   
     *   paiva1.equals(paiva2) === true;
     *   paiva2.equals(paiva1) === true;
     *   paiva1.equals(paiva3) === false;
     *   paiva3.equals(paiva2) === false;
     * </pre>
     */
    public boolean equals(Paiva paiva) {
        if ( paiva == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(paiva.anna(k)) ) return false;
        return true;
    }
    @Override
    public boolean equals(Object paiva) {
        if ( paiva instanceof Paiva ) return equals((Paiva)paiva);
        return false;
    }
    /**
     * Palauttaa virheen
     * @return virheen jos sellainen on olemassa, muuten sen arvo on null;
     */
    public String getVirhe(){
    	
    return virhe;
    }
    
    /**
     * Testi P‰‰ohjelma
     * @param args Ei k‰ytˆss‰
     */
    public static void main(String[] args) {
        Paiva paiva1 = new Paiva();
        Paiva paiva2 = new Paiva();
        
        paiva1.rekisteroi();
        paiva2.rekisteroi();

        paiva1.tulosta(System.out);
        paiva2.vastaaPaivan();
        paiva2.tulosta(System.out);
        
    }

}