package fxKuikanLava;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import LavaKirja.Paiva;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * 
 * @author Jere
 * @version 15.6.2017
 *
 */
public class TietojenLisaysController implements ModalControllerInterface<Paiva>,Initializable   {
    @FXML private Label labelVirhe;
    @FXML private GridPane gridPaiva;    
    @FXML private Label labelPaiva;    
    @FXML private Label LabelPaivamaara;   
    @FXML private TextField textPaivamaara;
    @FXML private TextField textLiput;
    @FXML private TextField textEtuliput;
    @FXML private TextField textBaaritulot;
    @FXML private TextField textKioskitulot;
    @FXML private TextField textPohjakassa;
    @FXML private TextField textEsiintyjahinta;
    @FXML private TextField textKioskimenot;
    @FXML private TextField textEtuliputhinta;
    @FXML private TextField textHinta;
    @FXML private TextField textBaaritmenot;
	@FXML private Button buttonPoistu;
	
	

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta(); 
        labelVirhe.setText("");
    }
    
    @FXML
    void handlePeruuta() {
        ModalController.closeStage(labelPaiva);
        paivaKohdalla = null;

    }

    @FXML
    void handleTallenna() {
        if ( paivaKohdalla != null && paivaKohdalla.getPaivamaara().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhj‰");
            return;
        }
       
        if(paivaKohdalla.getVirhe() != null) {
        	naytaVirhe("TƒƒLƒ ON VIRHE JOSSAKIN");
            return;
        }
        palautettava = paivaKohdalla;
        ModalController.closeStage(labelPaiva);
    }

//================

    private Paiva paivaKohdalla;
    private static Paiva apupaiva = new Paiva(); 
    private TextField[] edits;
    private int kentta = 0;
    private Paiva palautettava;
   
    /**
     * Luodaan GridPaneen p‰iv‰n tiedot
     * @param gridPaiva mihin tiedot luodaan
     * @return luodut tekstikent‰t
     */
    public static TextField[] luoKentat(GridPane gridPaiva) {
        gridPaiva.getChildren().clear();
        TextField[] edits = new TextField[apupaiva.getKenttia()];
       
        for (int i=0, k = apupaiva.ekaKentta(); k < apupaiva.getKenttia(); k++, i++) {
            Label label = new Label(apupaiva.getKysymys(k));
            gridPaiva.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridPaiva.add(edit, 1, i);
        }
        
       
        return edits;
    }
    
    
    /**
     * Tyhjent‰‰n tekstikent‰t
     * @param edits tyhjennett‰v‰t kent‰t
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit: edits) 
            if ( edit != null ) edit.setText(""); 
    }
    
    /**
     * Palautetaan komponentin id:st‰ saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mik‰ arvo jos id ei ole kunnollinen
     * @return komponentin id lukuna
     */
    public static int getFieldId(Object obj, int oletus) {
        if ( !( obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1),oletus);
    }
   
   
    /**
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikentt‰, johon voidaan tulostaa p‰iv‰n tiedot.
     */
    protected void alusta() {
        edits = luoKentat(gridPaiva);
        for (TextField edit : edits)
            if ( edit != null )
                edit.setOnKeyReleased( e -> kasitteleMuutosPaivaan((TextField)(e.getSource())));
    }
   
   
    @Override
    public void setDefault(Paiva oletus) {
        paivaKohdalla = oletus;
        naytaPaiva(edits, paivaKohdalla);
    }
   
    @Override
    public Paiva getResult() {
        return palautettava;
    }
   
   
    private void setKentta(int kentta) {
        this.kentta = kentta;
    }
   
   
    /**
     * Mit‰ tehd‰‰n kun dialogi on n‰ytetty
     */
    @Override
    public void handleShown() {
        kentta = Math.max(apupaiva.ekaKentta(), Math.min(kentta, apupaiva.getKenttia()-1));
        edits[kentta].requestFocus();
    }
   
   
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
   
    /**
     * K‰sitell‰‰n p‰iv‰‰n tullut muutos
     * @param edit muuttunut kentt‰
     */
    protected void kasitteleMuutosPaivaan(TextField edit) {
        if (paivaKohdalla == null) return;
        int k = getFieldId(edit,apupaiva.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = paivaKohdalla.aseta(k,s); 
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
   
   
    /**
     * N‰ytet‰‰n p‰iv‰n tiedot TextField komponentteihin
     * @param edits taulukko TextFieldeist‰ johon n‰ytet‰‰n
     * @param paiva n‰ytett‰v‰ p‰iva
     */
    public static void naytaPaiva(TextField[] edits, Paiva paiva) {
        if (paiva == null) return;
        for (int k = paiva.ekaKentta(); k < paiva.getKenttia(); k++) {
            edits[k].setText(paiva.anna(k));
        }
    }
   
   
    /**
     * Luodaan p‰iv‰n kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mit‰ dataan n‰ytet‰‰n oletuksena
     * @param kentta mik‰ kentt‰ saa fokuksen kun n‰ytet‰‰n
     * @return null jos painetaan Cancel, muuten t‰ytetty tietue
     */
    public static Paiva kysyPaiva(Stage modalityStage, Paiva oletus, int kentta) {
        return ModalController.<Paiva, TietojenLisaysController>showModal(
                    TietojenLisaysController.class.getResource("TietojenLisaysView.fxml"),
                    "Tietojen Lis‰‰minen",
                    modalityStage, oletus,
                    ctrl -> ctrl.setKentta(kentta) 
                );
    }


}

