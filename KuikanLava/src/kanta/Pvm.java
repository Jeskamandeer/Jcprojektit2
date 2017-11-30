package kanta;

/**
 * P�iv�n testaus luokka
 * @author hejajeco
 *
 */
public class Pvm {

    /** Taulukko kuukausien pituuksista. Oma rivi  karkausvuosille */
    public static final int KPITUUDET[][] = {
            // 1  2  3  4  5  6  7  8  9 10 11 12
            { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
            { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }
    };

    /**
     * Suorittaa p�iv�n tarkastuksen
     * @param p P�iv�
     * @param k Kuukausi
     * @param vv vuosi
     * @return null jos onnistuu muuten virhe
     */
    public String tarkista(int p, int k, int vv) {
     
        if ( k > 12  ) return "Kuukausia voi olla vain 12";
        if ( k < 1  ) return "Kuukaudet eiv�t voi olla negatiivisia";
        int kv = karkausvuosi(vv);
        int pv_lkm = KPITUUDET[kv][k - 1];
        if ( p > pv_lkm ) return "P�ivi� voi olla vain " +pv_lkm;
        if ( p < 1 ) return "P�iv�t eiv�t voi olla negatiivisa";

        return null;
    }

      /**
       * Tarkistaa onko vuosi karkausvuosi
       * @param vv tarkistettava vuosi	
       * @return 1 tai 0 jos vuosi on karkaus vuosi
       */
    public static int karkausvuosi(int vv) {
        if ( vv % 400 == 0 ) return 1;
        if ( vv % 100 == 0 ) return 0;
        if ( vv % 4 == 0 ) return 1;
        return 0;
    }

   



}