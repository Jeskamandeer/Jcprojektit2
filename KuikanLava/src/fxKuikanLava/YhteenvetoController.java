package fxKuikanLava;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.Node;
import java.net.URL;
import java.util.ResourceBundle;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import LavaKirja.LavaKirja;
import LavaKirja.Paiva;

/**
 * 
 * @author Jere
 * @version 15.6.2017
 *
 */
public class YhteenvetoController implements ModalControllerInterface<LavaKirja>, Initializable  {

    @FXML private Node textVastaus;
	@FXML private Text textLiput;
    @FXML private Text textAlennusliput;
    @FXML private Text textIlmaisliput;
    @FXML private Text textLiputyht;
    @FXML private Text textBaaritulot;
    @FXML private Text textKioskitulot;
    @FXML private Text textEsiintyja;
    @FXML private Text textMuuta;
    @FXML private Text textKassa;
    @FXML private Button buttonPoistu;
    @FXML private Label textYhteenveto;
    
    private LavaKirja lavakirja;       

    
    /**
     * Sulkee yhteenveto ikkunan
     */
    @FXML
    void handlePoistu() {  
        ModalController.closeStage(textYhteenveto);
    }
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta(); 
        
    }
    @Override
    public void handleShown() {
        //
    }

    /**
     * Alustaa yhteenvedon tiedot
     */
    protected void alusta() {
    	
        textLiput.setText("00");
        textAlennusliput.setText("00");
        textIlmaisliput.setText("00");
        textLiputyht.setText("00");
        textBaaritulot.setText("00");
        textKioskitulot.setText("00");
        textEsiintyja.setText("00");
        textMuuta.setText("00");
        textKassa.setText("00");  
       
       
        
    }
    

    @Override
    public void setDefault(LavaKirja tiedot) {
        this.lavakirja = tiedot;
        
        laske();
    }
    @Override
    public LavaKirja getResult() {
        return null;
    }
    /**
     * Laskee yhteenvedon kaikkien päivien pohjalta
     */
    public void laske(){
    	
        int baaritulot = 0;
        int alennusliput = 0;
        int liput = 0;
        int Ilmaisliput = 0;
        int LiputYht = 0;
        int kioskitulot = 0;
        int esiintyja = 0;
        int muuta = 0;
        int kassa = 0;
        
    	for (int i = 0; i < lavakirja.getPaivia(); i++){
    	
    	Paiva paiva = lavakirja.annaPaiva(i);
    	alennusliput += paiva.getEtuLiput();
    	baaritulot += paiva.getBaariTulot();
    	liput += paiva.getLiput();
    	Ilmaisliput += paiva.getIlmaisLiput();
    	kioskitulot += paiva.getKioskiTulot();
    	esiintyja += paiva.getEsiintyja();
    	muuta += paiva.getMenot();
    	kassa += paiva.getPohjaKassa();
    	
    	}
    	LiputYht += liput + alennusliput + alennusliput;
    	
    	textAlennusliput.setText("" + alennusliput);
    	textLiput.setText("" + liput);
    	textIlmaisliput.setText("" + Ilmaisliput);
    	textLiputyht.setText("" + LiputYht);
    	textBaaritulot.setText("" + baaritulot);
    	textKioskitulot.setText("" + kioskitulot);
    	textEsiintyja.setText("" + esiintyja);
    	textMuuta.setText("" + muuta);
    	textKassa.setText("" + kassa);
    }
}
 

