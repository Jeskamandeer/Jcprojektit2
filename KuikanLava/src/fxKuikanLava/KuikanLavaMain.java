package fxKuikanLava;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import LavaKirja.LavaKirja; 
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Jere
 * @version 30.5.2017
 *
 */
public class KuikanLavaMain extends Application {
	@Override
	/**
	 *  käynnistää kaiken
	 */
	public void start(Stage primaryStage) {
	    try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KuikanLavaGUIView.fxml")); 
            final Pane root = (Pane)ldr.load();
            final KuikanLavaGUIController lavaCtrl  = (KuikanLavaGUIController)ldr.getController(); 
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kuikanlava.css").toExternalForm()); 
            primaryStage.setScene(scene);
            primaryStage.setTitle("KuikanLava"); 
                       
            primaryStage.setOnCloseRequest((event) -> {
                
                if ( !lavaCtrl.voikoSulkea() ) event.consume(); 
            });
            
            LavaKirja lavakirja = new LavaKirja(); 
            lavaCtrl.setLavaKirja(lavakirja);
            
            primaryStage.show();
            
            Application.Parameters params = getParameters();
            if ( params.getRaw().size() > 0 )
                lavaCtrl.lueTiedosto(params.getRaw().get(0));
            else
                if ( !lavaCtrl.avaa() ) Platform.exit();
          
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * 
	 * @param args Pitää täyttää myöhemmin
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
