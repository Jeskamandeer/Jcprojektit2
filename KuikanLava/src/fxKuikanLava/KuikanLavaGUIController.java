package fxKuikanLava;
import javafx.fxml.FXML;

import LavaKirja.LavaKirja;
import LavaKirja.Paiva;
import LavaKirja.Esiintyja;


import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;


import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import kanta.SailoException;
import java.net.URL;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.ListChooser;


import static fxKuikanLava.TietojenLisaysController.*;  
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

/**
 * 
 * @author Jere
 * @version 30.5.2017
 *
 */
public class KuikanLavaGUIController implements Initializable {    
    @FXML private TextField hakuehto;  
    @FXML private Label labelVirhe; 
    @FXML private ListChooser<Paiva> chooserPaivat;
    @FXML private ScrollPane  panelPaiva;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private StringGrid<Esiintyja> tableEsiintyjat;
    @FXML private GridPane gridPaiva;
    
    @FXML private TextField textEtuHinta;
    @FXML private TextField textHinta;
    @FXML private TextField textEtuliput;
    @FXML private TextField textLiput;
    @FXML private TextField textKplYht;
    @FXML private TextField textEtuYht;
    @FXML private TextField textNormaaliYht;
    @FXML private TextField textPohjakassa;
    @FXML private TextField textYhteensa;
    @FXML private TextField textKioskiTulot;
    @FXML private TextField textKioskiMenot;
    @FXML private TextField textKioskiYht;
    @FXML private TextField textBaariYht;
    @FXML private TextField textBaariMenot;
     
        @Override
        public void initialize(URL url, ResourceBundle bundle) {
            alusta();     
        }
        
        @FXML private void handleHakuehto() {
            if ( paivaKohdalla != null )
                hae(paivaKohdalla.getTunnusNro()); 
        }
       /**
         * K‰sitell‰‰n uuden p‰iv‰n lis‰‰minen
         */
        @FXML private void handleUusiPaiva() {
            uusiPaiva();           
        }
        
        /**
         * K‰sitell‰‰n tallennusk‰sky
         */
        @FXML private void handleTallenna() {
            tallenna();
        }     
        /**
         * Tarkistetaan onko tallennus tehty
         * @return true jos saa sulkaa sovelluksen, false jos ei
         */
        public boolean voikoSulkea() {
            tallenna();
            return true;
        }      
        /**
         * Lis‰‰ esiintyj‰n
         */
        @FXML private void handleLisaaEsiintyja() {
            
            uusiEsiintyja();
        }
        /** 
         * lis‰‰ uuden p‰iv‰n tiedot
         */
        @FXML
        void handleLisaaUusi() {
            uusiPaiva();
        }              
        /** 
         * Muokkaa vanhoja tietoja
         */
        @FXML void handleMuokkaa() {
            muokkaa(kentta);

        }
        /** 
         * Poistaa tiedot
         */
        @FXML void handlePoista() {
            poistaPaiva(); 
        	
        }
        
        @FXML
        void handlePoistaEsiintyja() {
        	poistaEsiintyja();
        }
        
        
        /**
         * n‰ytt‰‰ tiedot ikkunan
         */
        @FXML void handleTietoa() {
            
            ModalController.showModal(KuikanLavaGUIController.class.getResource("TietojaView.fxml"), "Kuikan Lava", null, "");
        }
        /**
         * Luo yhteenvedon tietojen avulla
         */
        @FXML
        void handleYhteenveto() {
            ModalController.showModal(KuikanLavaGUIController.class.getResource("YhteenvetoView.fxml"), "Yhteenveto", null, lavakirja);

        }
        
        @FXML
        void handleApua() {
         Desktop desktop = Desktop.getDesktop();
         try {
         URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2017k/ht/hejajeco");
         desktop.browse(uri);
         } 
         catch (URISyntaxException e) {
             return;
             } catch (IOException e) {
             return;
          }       
  //===========================================================================================   
  // T‰st‰ eteenp‰in ei k‰yttˆliittym‰‰n suoraan liittyv‰‰ koodia          
         
        }       
        private LavaKirja lavakirja;
        private Paiva paivaKohdalla;
        private TextField[] edits; 
        private int kentta = 0; 
        
        private Paiva apuPaiva = new Paiva(); 
        private Esiintyja apuEsiintyja = new Esiintyja(); 
   
        /**
         * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
         * yksi iso tekstikentt‰, johon voidaan tulostaa p‰iv‰n tiedot.
         * Alustetaan myˆs p‰iv‰listan kuuntelija
         */   
        private void naytaVirhe(String virhe) {
            if ( virhe == null || virhe.isEmpty() ) {
                labelVirhe.setText(null);
                labelVirhe.getStyleClass().removeAll("virhe");
                return;
            }
            labelVirhe.setText(virhe);
            labelVirhe.getStyleClass().add("virhe");
        }
        /**
         * Tekee tarvittavat alustukset jotta ohjelma toimii
         */
        protected void alusta() {
            
            
            tableEsiintyjat.clear();
            chooserPaivat.clear();
            chooserPaivat.addSelectionListener(e -> naytaPaiva());
            
            edits = TietojenLisaysController.luoKentat(gridPaiva); 
            
            for (TextField edit: edits) 
                 if ( edit != null ) { 
                     edit.setEditable(false); 
                     edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); }); 
                     edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,0)); 
                 }   
            cbKentat.clear();
            for (int k = apuPaiva.ekaKentta(); k < apuPaiva.getKenttia(); k++) 
                cbKentat.add(apuPaiva.getKysymys(k), null);
            cbKentat.setSelectedIndex(0);
           
            String otsikot[] = new String[apuEsiintyja.getKenttia()-apuEsiintyja.ekaKentta()];
            for (int i=0, k=apuEsiintyja.ekaKentta(); k < apuEsiintyja.getKenttia(); i++, k++)
                otsikot[i] = apuEsiintyja.getKysymys(k);
            tableEsiintyjat.initTable(otsikot);
           
            tableEsiintyjat.setPlaceholder(new Label("Ei viel‰ esiintyji‰"));
            tableEsiintyjat.setOnCellString( (g, esiintyja, defValue, r, c) -> esiintyja.anna(c+esiintyja.ekaKentta()) );
           
            tableEsiintyjat.setOnGridLiveEdit((g, esiintyja, defValue, r, c, edit) -> {
                String virhe = esiintyja.aseta(c+esiintyja.ekaKentta(), defValue);
                if ( virhe == null ) {
                    edit.setStyle(null);
                    Dialogs.setToolTipText(edit,"");
                } else {
                    edit.setStyle("-fx-background-color: red");
                    Dialogs.setToolTipText(edit,virhe);
                }
                return defValue;
            });
        }
       /**
        * Muokkaa vanhaa
        * @param k kentt‰ jota muutetaan
        */
        private void muokkaa(int k) { 
             if ( paivaKohdalla == null ) return; 
             try { 
                 Paiva paiva; 
                 paiva = TietojenLisaysController.kysyPaiva(null, paivaKohdalla.clone(), k); 
                 if ( paiva == null ) return; 
                 lavakirja.korvaaTaiLisaa(paiva); 
                 hae(paiva.getTunnusNro()); 
             } catch (CloneNotSupportedException e) { 
                 //
             } catch (SailoException e) { 
                 Dialogs.showMessageDialog(e.getMessage()); 
             } 
         
        }
        /**
         * Lukee tiedostosta tiedot
         * @param nimi tiedosto joka luetaan
         * @return vihre tai null
         */
        protected String lueTiedosto(String nimi) {
            
            try {
                lavakirja.lueTiedostosta(nimi);
                hae(0);
                return null;
            } catch (SailoException e) {
                hae(0);
                String virhe = e.getMessage(); 
                if ( virhe != null ) Dialogs.showMessageDialog(virhe);
                return virhe;
            }
         }
        /**
         * Tallentaa tiedot tiedostoon
         * @return vihre jos ilmenee
         */
        private String tallenna() {
            try {
                lavakirja.tallenna();
                return null;
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("Talletuksessa ongelmia! " + ex.getMessage());
                return ex.getMessage();
            }
        }
        
        /**
         * N‰ytt‰‰ listasta valitun p‰iv‰n tiedot, tilap‰isesti yhteen isoon edit-kentt‰‰n
         */
        protected void naytaPaiva() {
            paivaKohdalla = chooserPaivat.getSelectedObject();    
            TietojenLisaysController.naytaPaiva(edits, paivaKohdalla); 
  
            naytaEsiintyjat(paivaKohdalla);
            gridPaiva.setVisible(paivaKohdalla != null); 
            naytaVirhe(null);
            
        }
        /**
         * Hakee p‰iv‰n tiedot listaan
         * @param jnro p‰iv‰n numero, joka aktivoidaan haun j‰lkeen
         */
        protected void hae(int jnro) {
            int k = cbKentat.getSelectedIndex() + apuPaiva.ekaKentta(); 
            String ehto = hakuehto.getText(); 
            if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*"; 
                naytaVirhe(null);
  
           
            chooserPaivat.clear();
            int index = 0;
            Collection<Paiva> Paivat;
            try {
                Paivat = lavakirja.etsi(ehto, k);
                int i = 0;
                for (Paiva paiva : Paivat) {
                    if (paiva.getTunnusNro() == jnro) index = i;
                    chooserPaivat.add(paiva.getPaivamaara(), paiva);
                    i++;
                }
                if ( i == 0 ) TietojenLisaysController.tyhjenna(edits); 
            } catch (SailoException ex) {
                Dialogs.showMessageDialog("P‰iv‰n hakemisessa ongelmia! " + ex.getMessage());
            }
            chooserPaivat.setSelectedIndex(index); 
        }
        /**
         * Luo uuden p‰iv‰n jota aletaan editoimaan
         */
        protected void uusiPaiva() {     

            try {
                Paiva uusi = new Paiva();
                uusi = TietojenLisaysController.kysyPaiva(null, uusi, 0); 
                if ( uusi == null ) return; 
                uusi.rekisteroi(); 
                lavakirja.lisaa(uusi);
                hae(uusi.getTunnusNro()); ;
            } catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                return;
            }
            
        }
        /**
         * Luo uuden esiintyjan
         */
        public void uusiEsiintyja() {
        
            if ( paivaKohdalla == null ) return; 
            String esiintyjanNimi = Dialogs.showInputDialog("Anna esiintyjan nimi", " ");
            Esiintyja esi = new Esiintyja();
            esi.vastaaEsiintyja(esiintyjanNimi);
          
            try {
                lavakirja.lisaa(paivaKohdalla, esi);
                } 
            catch (SailoException e) {
                Dialogs.showMessageDialog("Ongelmia lis‰‰misess‰! " + e.getMessage());
            } 
            hae(paivaKohdalla.getTunnusNro());    
          
        }
          
        /**
         * @param lavakirja Lavakirja jota k‰ytet‰‰n t‰ss‰ k‰yttˆliittym‰ss‰
         */
        public void setLavaKirja(LavaKirja lavakirja) {
            this.lavakirja = lavakirja;
            naytaPaiva();
        }
        /**
         * Tulostaa p‰ivien tiedot
         * @param os tietovirta johon tulostetaan
         * @param paiva tulostettava p‰iv‰
         */
        public void tulosta(PrintStream os, final Paiva paiva) {
            os.println("----------------------------------------------");
            paiva.tulosta(os);
            os.println("----------------------------------------------");
            
            List<Integer> loytyneet = lavakirja.hae(paiva.getTunnusNro());
            
            for (int d = 0; d < loytyneet.size(); d++){
              
                List<Esiintyja> esiintyjat = lavakirja.annaEsiintyjat(loytyneet.get(d));
                for( Esiintyja loytyi : esiintyjat)
                    loytyi.tulosta(os);
                                
            }
        }
        /**
         * Avaa tiedostot lukemista varten
         * @return true jos onnistuu
         */
        public boolean avaa() {
            String uusinimi = "Kuikan Lava";
           
            lueTiedosto(uusinimi);
            return true;
        
        }
        /**
         * N‰ytt‰‰ p‰iv‰n esiintyj‰t
         * @param paiva P‰iv‰ jota n‰ytet‰‰n
         */
        private void naytaEsiintyjat(Paiva paiva) {
            tableEsiintyjat.clear();
            if ( paiva == null ) return;
           
            List<Integer> loytyneet = lavakirja.hae(paiva.getTunnusNro());
            
            for (int d = 0; d < loytyneet.size(); d++){
                
                List<Esiintyja> esiintyjat = lavakirja.annaEsiintyjat(loytyneet.get(d));
                for( Esiintyja loytui : esiintyjat)
                    tableEsiintyjat.add(loytui);
                                
            }
            if ( loytyneet.size() == 0 ) return; 
        }
        /**
         * Poistaa esiintyjat p‰iv‰lt‰
         */
        private void poistaEsiintyja() {
            Esiintyja esi = tableEsiintyjat.getObject();
            if ( esi == null ) return;
            int rivi = tableEsiintyjat.getRowNr();
            lavakirja.poistaEsiintyja(esi);
            naytaEsiintyjat(paivaKohdalla);
            tableEsiintyjat.selectRow(rivi);
        }
        /**
         * Poistaa valitun p‰iv‰n
         */
        private void poistaPaiva() {
            if ( paivaKohdalla == null ) return;
            if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko p‰iv‰: " + paivaKohdalla.getPaivamaara(), "Kyll‰", "Ei") )
                return;
            lavakirja.poista(paivaKohdalla);
            int index = chooserPaivat.getSelectedIndex();
            hae(0);
            chooserPaivat.setSelectedIndex(index);
        }
    }

