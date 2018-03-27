package kanta;
/**
 * 
 * @author Jere
 * @version 31.7.2017
 *
 */
public class SisaltaaTarkistaja  {
    /**
     * Onko jonossa vain sallittuja merkkejä
     * @param jono      tutkittava jono
     * @param sallitut  merkit joita sallitaan
     * @return true jos vain sallittuja, false muuten
     */
    public static boolean onkoVain(String jono, String sallitut) {
        for (int i=0; i<jono.length(); i++)
            if ( sallitut.indexOf(jono.charAt(i)) < 0 ) return false;
        return true;
    }
    private final String sallitut;
    /**
     * Luodaan tarkistaja joka hyväksyy sallitut merkit
     * @param sallitut hyväksyttävät merkit
     */
    public SisaltaaTarkistaja(String sallitut) {
        this.sallitut = sallitut;
    }
    /**
     * Tarkistaa että jono sisältää vain valittuja numeroita
     * @param jono tutkittava jono
     * @return null jos vain valittujan merkkejä, muuten virheilmoitus
     */
    public String tarkista(String jono) {
        if ( onkoVain(jono, sallitut) ) return null;
        return "Saa olla vain merkkejä: " + sallitut;
    }
}